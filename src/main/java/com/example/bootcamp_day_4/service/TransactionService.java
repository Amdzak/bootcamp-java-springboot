package com.example.bootcamp_day_4.service;

import com.example.bootcamp_day_4.dto.TransactionItemRequest;
import com.example.bootcamp_day_4.dto.TransactionRequest;
import com.example.bootcamp_day_4.entity.Product;
import com.example.bootcamp_day_4.entity.TransactionDetail;
import com.example.bootcamp_day_4.entity.TransactionHistory;
import com.example.bootcamp_day_4.repository.ProductRepository;
import com.example.bootcamp_day_4.repository.TransactionDetailRepository;
import com.example.bootcamp_day_4.repository.TransactionHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final static Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final TransactionDetailRepository transactionDetailRepository;
    private final ProductRepository productRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topic}")
    private String kafkaTopicName;

    @Transactional
    public void createTransaction(TransactionRequest request) {
        logger.info("Step 1: Validating and Saving Transaction Header");

        // 1. Simpan Header Transaksi (Status: PENDING/CREATED)
        TransactionHistory history = new TransactionHistory();
        history.setTransactionDate(LocalDate.now());
        history.setTotalPrice(BigDecimal.ZERO);
        TransactionHistory savedHistory = transactionHistoryRepository.save(history);

        BigDecimal grandTotal = BigDecimal.ZERO;

        // 2. Simpan Detail Transaksi & Validasi awal
        for (TransactionItemRequest item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

            // Validasi stok di awal (biar tidak kirim sampah ke Kafka)
            if (product.getCurrentStock() < item.getQuantity()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Out of stock");
            }

            BigDecimal subTotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            grandTotal = grandTotal.add(subTotal);

            TransactionDetail detail = new TransactionDetail();
            detail.setTransaction(savedHistory);
            detail.setProduct(product);
            detail.setQty(item.getQuantity());
            detail.setPrice(product.getPrice());
            detail.setTotalPrice(subTotal);
            transactionDetailRepository.save(detail);
        }

        savedHistory.setTotalPrice(grandTotal);
        transactionHistoryRepository.save(savedHistory);

        // 3. Step 2 di gambar: Kirim data ke Kafka (PENTING!)
        try {
            // Kita kirim savedHistory yang sudah punya ID dan Detail
            kafkaTemplate.send(kafkaTopicName, savedHistory.getId().toString(), savedHistory);
            logger.info("Step 2: Message sent to Kafka for Transaction ID: {}", savedHistory.getId());
        } catch (Exception e) {
            logger.error("Failed to send to Kafka: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Kafka Error");
        }
    }
}
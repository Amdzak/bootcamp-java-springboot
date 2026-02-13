package com.example.bootcamp_day_4.service;

import com.example.bootcamp_day_4.dto.TransactionItemRequest;
import com.example.bootcamp_day_4.dto.TransactionKafkaMessage;
import com.example.bootcamp_day_4.dto.TransactionRequest;
import com.example.bootcamp_day_4.entity.Product;
import com.example.bootcamp_day_4.entity.TransactionDetail;
import com.example.bootcamp_day_4.entity.TransactionHistory;
import com.example.bootcamp_day_4.repository.ProductRepository;
import com.example.bootcamp_day_4.repository.TransactionDetailRepository;
import com.example.bootcamp_day_4.repository.TransactionHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionHistoryRepository transactionHistoryRepository;
    private final TransactionDetailRepository transactionDetailRepository;
    private final ProductRepository productRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topic}")
    private String kafkaTopicName;

    @Transactional
    public void createTransaction(TransactionRequest request) {
        log.info("Step 1: Validating and Saving Transaction Header");

        // Save header transaction (Status: PENDING/CREATED)
        TransactionHistory history = new TransactionHistory();
        history.setTransactionDate(LocalDate.now());
        history.setTotalPrice(BigDecimal.ZERO);
        TransactionHistory savedHistory = transactionHistoryRepository.save(history);

        BigDecimal grandTotal = BigDecimal.ZERO;
        List<TransactionDetail> details = new ArrayList<>();

        // Save detail transaction
        for (TransactionItemRequest item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

            // Validate stock
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
            details.add(detail);
        }

        savedHistory.setTotalPrice(grandTotal);
        transactionHistoryRepository.save(savedHistory);

        // Build transaction event payload for Kafka publishing
        TransactionKafkaMessage kafkaMessage = TransactionKafkaMessage.builder()
                .id(savedHistory.getId())
                .transactionDate(savedHistory.getTransactionDate().toString())
                .totalPrice(savedHistory.getTotalPrice())
                .items(details.stream().map(d -> TransactionKafkaMessage.TransactionItemMessage.builder()
                        .productId(d.getProduct().getId())
                        .productName(d.getProduct().getProductName())
                        .qty(d.getQty())
                        .price(d.getPrice())
                        .build()).toList())
                .build();

        // Sending data to kafka
        try {
            kafkaTemplate.send(kafkaTopicName, savedHistory.getId().toString(), kafkaMessage);
            log.info(" Message sent to Kafka for Transaction ID: {}", savedHistory.getId());
        } catch (Exception e) {
            log.error("Failed to send to Kafka: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Kafka Error");
        }
    }
}
package com.example.bootcamp_day_4.consumer;

import com.example.bootcamp_day_4.entity.*;
import com.example.bootcamp_day_4.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionConsumer {

    private final static Logger logger = LoggerFactory.getLogger(TransactionConsumer.class);
    private final ProductRepository productRepository;
    private final StockLogRepository stockLogRepository;
    private final TransactionDetailRepository transactionDetailRepository;

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    @Transactional
    public void consumeTransaction(TransactionHistory transactionFromKafka) {
        logger.info("Step 3: Consumer received transaction ID: {}", transactionFromKafka.getId());

        // Ambil detail transaksi dari database berdasarkan ID yang dikirim Kafka
        // (Atau bisa juga kirim DTO lengkap lewat Kafka)
        var details = transactionDetailRepository.findByTransactionId(transactionFromKafka.getId());

        for (TransactionDetail detail : details) {
            Product product = detail.getProduct();

            // LOGIKA UPDATE STOCK (Sesuai Gambar)
            int oldStock = product.getCurrentStock();
            int newStock = oldStock - detail.getQty();
            product.setCurrentStock(newStock);
            productRepository.save(product);

            logger.info("Updated Stock for {}: {} -> {}", product.getProductName(), oldStock, newStock);

            // LOGIKA INSERT STOCK LOG (Sesuai Gambar)
            StockLog log = new StockLog();
            log.setProduct(product);
            log.setQuantityChange(-detail.getQty());
            log.setLogType("SALE");
            stockLogRepository.save(log);
        }

        logger.info("Step 4: All DB Updates completed by Consumer");
    }
}
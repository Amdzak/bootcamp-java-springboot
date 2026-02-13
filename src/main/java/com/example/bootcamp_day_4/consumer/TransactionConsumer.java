package com.example.bootcamp_day_4.consumer;

import com.example.bootcamp_day_4.entity.*;
import com.example.bootcamp_day_4.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionConsumer {

    private final ProductRepository productRepository;
    private final StockLogRepository stockLogRepository;
    private final TransactionDetailRepository transactionDetailRepository;

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    @Transactional
    public void consumeTransaction(TransactionHistory transactionFromKafka) {
        log.info("Consumer received transaction ID: {}", transactionFromKafka.getId());

        // Get detail transaction from database where ID Kafka sended
        var details = transactionDetailRepository.findByTransactionId(transactionFromKafka.getId());

        for (TransactionDetail detail : details) {
            Product product = detail.getProduct();

            // Update stock
            int oldStock = product.getCurrentStock();
            int newStock = oldStock - detail.getQty();
            product.setCurrentStock(newStock);
            productRepository.save(product);

            log.info("Updated Stock for {}: {} -> {}", product.getProductName(), oldStock, newStock);

            // Create stock log with type SALE
            StockLog log = new StockLog();
            log.setProduct(product);
            log.setQuantityChange(-detail.getQty());
            log.setLogType("SALE");
            stockLogRepository.save(log);
        }

        log.info("All DB Updates completed by Consumer");
    }
}
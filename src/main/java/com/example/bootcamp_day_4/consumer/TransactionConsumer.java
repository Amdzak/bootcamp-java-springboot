package com.example.bootcamp_day_4.consumer;

import com.example.bootcamp_day_4.dto.TransactionKafkaMessage;
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
    public void consumeTransaction(TransactionKafkaMessage message) {
        log.info("Consumer received Fat Message for ID: {}", message.getId());

        for (var item : message.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Update Stock
            int newStock = product.getCurrentStock() - item.getQty();
            product.setCurrentStock(newStock);
            productRepository.save(product);

            // Insert Stock Log
            StockLog logEntry = new StockLog();
            logEntry.setProduct(product);
            logEntry.setQuantityChange(-item.getQty());
            logEntry.setLogType("SALE");
            stockLogRepository.save(logEntry);
        }
        log.info("Inventory updated without extra DB queries!");
    }
}
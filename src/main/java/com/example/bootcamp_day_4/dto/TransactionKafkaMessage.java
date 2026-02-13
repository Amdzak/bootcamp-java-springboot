package com.example.bootcamp_day_4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionKafkaMessage {
    private Long id;
    private String transactionDate;
    private BigDecimal totalPrice;
    private List<TransactionItemMessage> items;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TransactionItemMessage {
        private Long productId;
        private String productName;
        private Integer qty;
        private BigDecimal price;
    }
}
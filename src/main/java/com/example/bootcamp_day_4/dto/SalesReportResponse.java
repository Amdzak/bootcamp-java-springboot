package com.example.bootcamp_day_4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SalesReportResponse {
    private Long transactionId;
    private LocalDate transactionDate;
    private String productName;
    private Integer qty;
    private BigDecimal price;
    private BigDecimal totalPrice;
}
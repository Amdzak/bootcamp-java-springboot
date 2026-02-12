package com.example.bootcamp_day_4.dto;

import lombok.Data;

import java.util.List;

@Data
public class TransactionRequest {
    private List<TransactionItemRequest> items;
    // Total harga biasanya dihitung di Service agar lebih aman (tidak manipulasi dari front-end)
}

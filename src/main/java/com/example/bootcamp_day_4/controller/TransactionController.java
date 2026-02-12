package com.example.bootcamp_day_4.controller;

import com.example.bootcamp_day_4.dto.TransactionRequest;
import com.example.bootcamp_day_4.dto.WebResponse;
import com.example.bootcamp_day_4.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> create(@RequestBody TransactionRequest request) {
        log.info("Create transaction request received | totalItems={}", request.getItems().size());
        transactionService.createTransaction(request);
        return WebResponse.<String>builder()
                .message("Transaction success data send to Kafka")
                .data("OK")
                .build();
    }
}
package com.example.bootcamp_day_4.service;

import com.example.bootcamp_day_4.dto.SalesReportResponse;
import com.example.bootcamp_day_4.entity.StockLog;
import com.example.bootcamp_day_4.repository.StockLogRepository;
import com.example.bootcamp_day_4.repository.TransactionDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private final StockLogRepository stockLogRepository;
    private final TransactionDetailRepository transactionDetailRepository;

    // Get data for stock log report with date parameter
    public List<StockLog> getStockLogReport(LocalDate startDate, LocalDate endDate) {
        log.info("Generating Stock Log Report from {} to {}", startDate, endDate);

        // Conversion LocalDate to LocalDateTime
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        List<StockLog> logs = stockLogRepository.findByCreatedAtBetween(start, end);

        log.info("Stock Log Report generated successfully. Found {} log entries", logs.size());
        return logs;
    }

    // Generate sales report for the specified date range
    public List<SalesReportResponse> getSalesReport(LocalDate startDate, LocalDate endDate) {
        log.info("Generating sales report from {} to {}", startDate, endDate);
        return transactionDetailRepository.getSalesReport(startDate, endDate);
    }
}
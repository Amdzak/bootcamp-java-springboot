package com.example.bootcamp_day_4.service;

import com.example.bootcamp_day_4.entity.StockLog;
import com.example.bootcamp_day_4.entity.TransactionHistory;
import com.example.bootcamp_day_4.repository.StockLogRepository;
import com.example.bootcamp_day_4.repository.TransactionHistoryRepository;
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

    private final TransactionHistoryRepository transactionHistoryRepository;
    private final StockLogRepository stockLogRepository;

    /**
     * API GET for report sales with date parameter
     * Mengambil semua sejarah transaksi dalam rentang tanggal tertentu
     */
    public List<TransactionHistory> getSalesReport(LocalDate startDate, LocalDate endDate) {
        log.info("Generating Sales Report from {} to {}", startDate, endDate);

        List<TransactionHistory> report = transactionHistoryRepository.findByTransactionDateBetween(startDate, endDate);

        log.info("Sales Report generated successfully. Found {} transactions", report.size());
        return report;
    }

    /**
     * API GET data for stock log report with date parameter
     * Karena StockLog menggunakan LocalDateTime, kita konversi LocalDate menjadi
     * awal hari (00:00) dan akhir hari (23:59)
     */
    public List<StockLog> getStockLogReport(LocalDate startDate, LocalDate endDate) {
        log.info("Generating Stock Log Report from {} to {}", startDate, endDate);

        // Konversi LocalDate ke LocalDateTime agar akurat dalam pencarian database
        LocalDateTime start = startDate.atStartOfDay(); // 2026-02-12 00:00:00
        LocalDateTime end = endDate.atTime(LocalTime.MAX); // 2026-02-12 23:59:59

        List<StockLog> logs = stockLogRepository.findByCreatedAtBetween(start, end);

        log.info("Stock Log Report generated successfully. Found {} log entries", logs.size());
        return logs;
    }
}
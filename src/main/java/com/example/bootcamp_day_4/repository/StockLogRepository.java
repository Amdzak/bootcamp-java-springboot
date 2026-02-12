package com.example.bootcamp_day_4.repository;

import com.example.bootcamp_day_4.entity.StockLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockLogRepository extends JpaRepository<StockLog, Long> {
    // Filter berdasarkan range tanggal dan tipe log (SALE/PURCHASE/ADJUSTMENT)
    List<StockLog> findByLogTypeAndCreatedAtBetween(String logType, LocalDateTime start, LocalDateTime end);

    // Untuk kebutuhan Report Stock Log berdasarkan range tanggal
    List<StockLog> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
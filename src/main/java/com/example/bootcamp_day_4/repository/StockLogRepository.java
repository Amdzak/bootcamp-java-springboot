package com.example.bootcamp_day_4.repository;

import com.example.bootcamp_day_4.entity.StockLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockLogRepository extends JpaRepository<StockLog, Long> {

    // Retrieve stock movement logs within a specified date range for reporting
    List<StockLog> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
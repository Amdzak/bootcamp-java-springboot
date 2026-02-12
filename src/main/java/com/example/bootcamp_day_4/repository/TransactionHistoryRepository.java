package com.example.bootcamp_day_4.repository;

import com.example.bootcamp_day_4.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
    // Query untuk Report Sales berdasarkan range tanggal
    List<TransactionHistory> findByTransactionDateBetween(LocalDate startDate, LocalDate endDate);
}
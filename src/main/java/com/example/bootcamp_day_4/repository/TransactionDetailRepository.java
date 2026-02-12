package com.example.bootcamp_day_4.repository;

import com.example.bootcamp_day_4.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Long> {
    List<TransactionDetail> findByTransactionId(Long transactionId);

    // Tambahan: Untuk melihat performa penjualan produk tertentu
    List<TransactionDetail> findByProductId(Long productId);
}
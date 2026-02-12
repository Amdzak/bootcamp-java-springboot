package com.example.bootcamp_day_4.repository;

import com.example.bootcamp_day_4.dto.SalesReportResponse;
import com.example.bootcamp_day_4.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Long> {
    List<TransactionDetail> findByTransactionId(Long transactionId);

    // Tambahan: Untuk melihat performa penjualan produk tertentu
    List<TransactionDetail> findByProductId(Long productId);

    @Query("SELECT new com.example.bootcamp_day_4.dto.SalesReportResponse(" +
            "d.transaction.id, d.transaction.transactionDate, p.productName, " +
            "d.qty, d.price, d.totalPrice) " +
            "FROM TransactionDetail d JOIN d.product p " +
            "WHERE d.transaction.transactionDate BETWEEN :startDate AND :endDate")
    List<SalesReportResponse> getSalesReport(@Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);
}
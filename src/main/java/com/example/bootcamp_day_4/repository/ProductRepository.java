package com.example.bootcamp_day_4.repository;

import com.example.bootcamp_day_4.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Search by SKU
    Optional<Product> findBySku(String sku);

    // Find product less than
    List<Product> findByCurrentStockLessThan(Integer threshold);
}
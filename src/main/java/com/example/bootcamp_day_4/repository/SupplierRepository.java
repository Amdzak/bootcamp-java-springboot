package com.example.bootcamp_day_4.repository;

import com.example.bootcamp_day_4.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    // Retrieve all active suppliers (excluding soft-deleted records)
    List<Supplier> findByDeletedAtIsNull();
}
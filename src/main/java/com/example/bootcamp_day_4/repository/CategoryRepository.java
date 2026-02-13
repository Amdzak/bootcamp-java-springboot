package com.example.bootcamp_day_4.repository;

import com.example.bootcamp_day_4.entity.Category;
import com.example.bootcamp_day_4.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Find non-deleted category
    List<Category> findByDeletedAtIsNull();
}

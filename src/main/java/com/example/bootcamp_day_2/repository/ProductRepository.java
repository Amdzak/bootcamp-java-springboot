package com.example.bootcamp_day_2.repository;

import com.example.bootcamp_day_2.entity.products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<products, Long> {

    products findByName(String name);

}

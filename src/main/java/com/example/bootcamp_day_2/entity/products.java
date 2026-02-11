package com.example.bootcamp_day_2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "products")
public class products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Mapping dari BIGSERIAL

    @Column(nullable = false, length = 255)
    private String name; // Mapping dari VARCHAR(255) NOT NULL

    @Column(columnDefinition = "TEXT")
    private String description; // Mapping dari TEXT

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price; // Mapping dari DECIMAL(10,2) NOT NULL

    @Column(nullable = false)
    private Integer stock = 0; // Mapping dari INTEGER DEFAULT 0

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Mapping dari TIMESTAMP

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now(); // Mapping dari TIMESTAMP
}

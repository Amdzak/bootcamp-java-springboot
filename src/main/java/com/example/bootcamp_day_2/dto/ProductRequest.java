package com.example.bootcamp_day_2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank
    @Size(max = 255, message = "Nama produk maksimal 255 karakter")
    private String name;

    private String description;

    @NotNull(message = "Harga tidak boleh kosong")
    @PositiveOrZero(message = "Harga harus lebih besar atau sama dengan 0")
    private BigDecimal price;

    @NotNull(message = "Stok tidak boleh kosong")
    @PositiveOrZero(message = "Stok tidak boleh negatif")
    private Integer stock;
}

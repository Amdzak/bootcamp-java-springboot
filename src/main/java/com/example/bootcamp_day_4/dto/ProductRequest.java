package com.example.bootcamp_day_4.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotBlank(message = "SKU wajib diisi")
    private String sku;

    @NotBlank(message = "Nama produk wajib diisi")
    private String productName;

    @NotNull(message = "Category ID wajib diisi")
    private Long categoryId;

    @NotNull(message = "Supplier ID wajib diisi")
    private Long supplierId;

    @NotNull(message = "Stok awal wajib diisi")
    @Min(value = 0, message = "Stok tidak boleh negatif")
    private Integer currentStock;

    @NotNull(message = "Harga wajib diisi")
    private BigDecimal price;
}
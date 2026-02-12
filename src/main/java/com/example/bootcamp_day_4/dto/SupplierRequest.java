package com.example.bootcamp_day_4.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierRequest {
    @NotBlank(message = "Nama supplier tidak boleh kosong")
    @Size(max = 100)
    private String supplierName;

    private String contact;

    private String address;
}
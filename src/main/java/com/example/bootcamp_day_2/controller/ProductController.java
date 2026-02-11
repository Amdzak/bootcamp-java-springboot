package com.example.bootcamp_day_2.controller;

import com.example.bootcamp_day_2.dto.ProductRequest;
import com.example.bootcamp_day_2.dto.ProductRespond;
import com.example.bootcamp_day_2.entity.products;
import com.example.bootcamp_day_2.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "java bootcamp 2026")
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // --- CREATE ---
    @Operation(summary = "Create a new product")
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductRespond<String> createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
        return ProductRespond.<String>builder().message("Product successfully created").data(null).build();
    }

    // --- READ ALL ---
    @Operation(summary = "Get all products")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductRespond<List<products>> getAllProducts() {
        List<products> data = productService.getAllProducts();
        return ProductRespond.<List<products>>builder().message("Success fetch all products").data(data).build();
    }

    // --- READ BY ID ---
    @Operation(summary = "Get product by ID")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductRespond<products> getProductById(@PathVariable Long id) {
        products data = productService.getProductById(id);
        return ProductRespond.<products>builder().message("Success fetch product").data(data).build();
    }

    // --- UPDATE BY NAME ---
    @Operation(summary = "Update product by its current name")
    @PutMapping(path = "/update/{name}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductRespond<String> updateProduct(@PathVariable String name, @RequestBody ProductRequest productRequest) {
        productService.updateByName(name, productRequest);
        return ProductRespond.<String>builder().message("Product successfully updated").data(null).build();
    }

    // --- DELETE BY ID ---
    @Operation(summary = "Delete product by ID")
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductRespond<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ProductRespond.<String>builder().message("Product successfully deleted").data(null).build();
    }
}

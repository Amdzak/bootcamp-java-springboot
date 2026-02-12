package com.example.bootcamp_day_4.controller;

import com.example.bootcamp_day_4.dto.ProductRequest;
import com.example.bootcamp_day_4.dto.WebResponse;
import com.example.bootcamp_day_4.entity.Product;
import com.example.bootcamp_day_4.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> create(@RequestBody ProductRequest request) {
        log.info("Request create product: {}", request.getProductName());
        productService.createProduct(request);
        return WebResponse.<String>builder().message("Success create product").data("OK").build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<Product>> getAll() {
        log.info("Fetching all products");
        return WebResponse.<List<Product>>builder()
                .message("Success get all products")
                .data(productService.getAllProducts())
                .build();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<Product> getById(@PathVariable Long id) {
        log.info("Fetching product with id={}", id);
        return WebResponse.<Product>builder()
                .message("Success get product")
                .data(productService.getProductById(id))
                .build();
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> update(@PathVariable Long id, @RequestBody ProductRequest request) {
        log.info("Updating product id={}", id);
        productService.updateProductById(id, request);
        return WebResponse.<String>builder().message("Success Update Product").data("OK").build();
    }

    @PatchMapping(path = "/{id}/adjust-stock")
    public WebResponse<String> adjustStock(@PathVariable Long id, @RequestParam("actual_stock") Integer actualStock) {
        log.warn("Adjust stock with id={}", id);
        productService.adjustStock(id, actualStock);
        return WebResponse.<String>builder()
                .message("Success adjust stock")
                .data("OK")
                .build();
    }

    @PostMapping(path = "/{id}/purchase")
    public WebResponse<String> purchase(@PathVariable Long id, @RequestParam("quantity") Integer quantity) {
        log.info("Purchase stock with id={}", id);
        productService.purchaseStock(id, quantity);
        return WebResponse.<String>builder()
                .message("Success purchase product")
                .data("OK")
                .build();
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> delete(@PathVariable Long id) {
        log.warn("Deleting product id={}", id);
        productService.deleteProductById(id);
        return WebResponse.<String>builder().message("Success delete product").data("OK").build();
    }
}
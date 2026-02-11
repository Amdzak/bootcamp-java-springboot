package com.example.bootcamp_day_2.service;

import com.example.bootcamp_day_2.dto.ProductRequest;
import com.example.bootcamp_day_2.repository.ProductRepository;
import jakarta.transaction.Transactional;
import com.example.bootcamp_day_2.entity.products;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    private final static Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Validator validator;

    // Create Product
    @Transactional
    public void createProduct(ProductRequest productRequest){
        logger.info("Starting to create product with name: {}", productRequest.getName());

        Set<ConstraintViolation<ProductRequest>> violation = validator.validate(productRequest);

        if (!violation.isEmpty()){
            logger.error("Create failed: Product with missing value");
            throw new ConstraintViolationException(violation);
        }

        products existProduct = productRepository.findByName(productRequest.getName());
        if (existProduct != null){
            logger.error("Create failed: Product with name {} already exists", productRequest.getName());
            throw new IllegalArgumentException("Product with name " + productRequest.getName() + " already exist");
        }

        products product = new products();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());

        productRepository.save(product);
        logger.info("Product created successfully with ID: {}", product.getId());
    }

    // Read All Product
    public List<products> getAllProducts(){
        logger.info("Fetching all products from database");
        List<products> productList = productRepository.findAll();
        logger.info("Successfully fetched {} products", productList.size());
        return productList;
    }

    // Read Product By Id
    public products getProductById(Long id) {
        logger.info("Fetching product with ID: {}", id);
        return productRepository.findById(id)
                .map(product -> {
                    logger.info("Product found: {}", product.getName());
                    return product;
                })
                .orElseThrow(() -> {
                    logger.warn("Fetch failed: Product with ID {} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
                });
    }

    // Update Product By Name
    @Transactional
    public void updateByName(String name, ProductRequest productRequest){
        logger.info("Starting update process for product name: {}", name);
        validateRequest(productRequest);

        products product = productRepository.findByName(name);
        if (product == null) {
            logger.warn("Update failed: Product with name {} not found", name);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        // Cek duplikasi jika nama diubah
        products duplicateName = productRepository.findByName(productRequest.getName());
        if (duplicateName != null && !duplicateName.getId().equals(product.getId())) {
            logger.error("Update failed: New name {} is already used by another product ID: {}",
                    productRequest.getName(), duplicateName.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product name already used by another product");
        }

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());

        productRepository.save(product);
        logger.info("Product with ID: {} updated successfully", product.getId());
    }

    // Delete Product By Name
    @Transactional
    public void deleteProduct(Long id) {
        logger.info("Attempting to delete product with ID: {}", id);
        if (!productRepository.existsById(id)) {
            logger.warn("Delete failed: Product with ID {} does not exist", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        productRepository.deleteById(id);
        logger.info("Product with ID: {} deleted successfully", id);
    }

    // Helper function
    private void validateRequest(ProductRequest productRequest) {
        logger.debug("Validating ProductRequest for: {}", productRequest.getName());
        Set<ConstraintViolation<ProductRequest>> violation = validator.validate(productRequest);

        if (!violation.isEmpty()){
            logger.error("Validation failed: {} violations found", violation.size());
            throw new ConstraintViolationException(violation);
        }
    }
}

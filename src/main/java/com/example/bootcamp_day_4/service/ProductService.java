package com.example.bootcamp_day_4.service;

import com.example.bootcamp_day_4.dto.ProductRequest;
import com.example.bootcamp_day_4.entity.Category;
import com.example.bootcamp_day_4.entity.Product;
import com.example.bootcamp_day_4.entity.StockLog;
import com.example.bootcamp_day_4.entity.Supplier;
import com.example.bootcamp_day_4.repository.CategoryRepository;
import com.example.bootcamp_day_4.repository.ProductRepository;
import com.example.bootcamp_day_4.repository.StockLogRepository;
import com.example.bootcamp_day_4.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private StockLogRepository stockLogRepository;

    @Autowired
    private Validator validator;

    @Transactional
    public void createProduct(ProductRequest productRequest) {
        log.info("Starting to create product with SKU: {}", productRequest.getSku());

        validate(productRequest);

        // Check SKU is existing
        if (productRepository.findBySku(productRequest.getSku()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SKU already exists");
        }

        // Check category and supplier
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        Supplier supplier = supplierRepository.findById(productRequest.getSupplierId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));

        // Create
        Product product = new Product();
        product.setSku(productRequest.getSku());
        product.setProductName(productRequest.getProductName());
        product.setPrice(productRequest.getPrice());
        product.setCurrentStock(productRequest.getCurrentStock());
        product.setCategory(category);
        product.setSupplier(supplier);

        productRepository.save(product);

        // Create stock log with type PURCHASE
        StockLog stockLog = new StockLog();
        stockLog.setProduct(product);
        stockLog.setQuantityChange(productRequest.getCurrentStock());
        stockLog.setLogType("PURCHASE");
        stockLogRepository.save(stockLog);

        log.info("Product created successfully with ID: {}", product.getId());
    }

    @Transactional
    public void adjustStock(Long productId, Integer actualStock) {
        log.info("Starting to adjust stock with id: {}",productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        int discrepancy = actualStock - product.getCurrentStock();

        if (discrepancy == 0) return;

        // Update stock product
        product.setCurrentStock(actualStock);
        productRepository.save(product);

        // Create stock log with type ADJUSTMENT
        StockLog stockLog = new StockLog();
        stockLog.setProduct(product);
        stockLog.setQuantityChange(discrepancy);
        stockLog.setLogType("ADJUSTMENT");
        stockLogRepository.save(stockLog);

        log.info("Stock adjusted for {}. Discrepancy: {}", product.getProductName(), discrepancy);
    }

    @Transactional
    public void purchaseStock(Long productId, Integer quantityReceived) {
        log.info("Starting to create add new product with id: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        // Add stock from supplier
        int oldStock = product.getCurrentStock();
        product.setCurrentStock(oldStock + quantityReceived);
        productRepository.save(product);

        // Cretae stock log with type PURCHASE
        StockLog stockLog = new StockLog();
        stockLog.setProduct(product);
        stockLog.setQuantityChange(quantityReceived);
        stockLog.setLogType("PURCHASE");
        stockLogRepository.save(stockLog);

        log.info("Purchase successful for {}. Added: {}, New Total: {}",
                product.getProductName(), quantityReceived, product.getCurrentStock());
    }

    public List<Product> getAllProducts() {
        return productRepository.findByDeletedAtIsNull();
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @Transactional
    public void updateProductById(Long productId, ProductRequest request) {
        log.info("Starting to update product with id: {}",productId);
        validate(request);
        Product product = getProductById(productId);

        // Update product
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setSku(request.getSku());

        // Update relation where ID change
        if (!product.getCategory().getId().equals(request.getCategoryId())) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
            product.setCategory(category);
        }

        productRepository.save(product);
        log.info("Product updated: {}", productId);
    }

    @Transactional
    public void deleteProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        product.setDeletedAt(LocalDateTime.now());
        log.info("Product deleted: {}", productId);
    }

    // Helper function
    private void validate(ProductRequest productRequest) {
        Set<ConstraintViolation<ProductRequest>> violation = validator.validate(productRequest);
        if (!violation.isEmpty()){
            throw new ConstraintViolationException(violation);
        }
    }
}

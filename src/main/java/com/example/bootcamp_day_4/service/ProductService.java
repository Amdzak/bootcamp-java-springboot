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
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private StockLogRepository stockLogRepository;

    @Autowired
    private Validator validator;

    @Transactional
    public void createProduct(ProductRequest productRequest) {
        logger.info("Starting to create product with SKU: {}", productRequest.getSku());

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

        // 5. Catat Log Stok Awal (Sesuai dokumen: PURCHASE/ADJUSTMENT)
        StockLog log = new StockLog();
        log.setProduct(product);
        log.setQuantityChange(productRequest.getCurrentStock());
        log.setLogType("PURCHASE");
        stockLogRepository.save(log);

        logger.info("Product created successfully with ID: {}", product.getId());
    }

    @Transactional
    public void adjustStock(Long productId, Integer actualStock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        int discrepancy = actualStock - product.getCurrentStock();

        if (discrepancy == 0) return;

        // 1. Update stok produk ke angka fisik yang baru
        product.setCurrentStock(actualStock);
        productRepository.save(product);

        // 2. Catat ke Stock Log dengan tipe ADJUSTMENT
        StockLog log = new StockLog();
        log.setProduct(product);
        log.setQuantityChange(discrepancy);
        log.setLogType("ADJUSTMENT");
        stockLogRepository.save(log);

        logger.info("Stock adjusted for {}. Discrepancy: {}", product.getProductName(), discrepancy);
    }

    @Transactional
    public void purchaseStock(Long productId, Integer quantityReceived) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        // 1. Tambahkan stok yang ada dengan stok baru yang datang
        int oldStock = product.getCurrentStock();
        product.setCurrentStock(oldStock + quantityReceived);
        productRepository.save(product);

        // 2. Catat ke Stock Log dengan tipe PURCHASE
        StockLog log = new StockLog();
        log.setProduct(product);
        log.setQuantityChange(quantityReceived);
        log.setLogType("PURCHASE");
        stockLogRepository.save(log);

        logger.info("Purchase successful for {}. Added: {}, New Total: {}",
                product.getProductName(), quantityReceived, product.getCurrentStock());
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @Transactional
    public void updateProductById(Long id, ProductRequest request) {
        validate(request);
        Product product = getProductById(id);

        // Update
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setSku(request.getSku());

        // Update relasi jika ID berubah
        if (!product.getCategory().getId().equals(request.getCategoryId())) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
            product.setCategory(category);
        }

        productRepository.save(product);
        logger.info("Product updated: {}", id);
    }

    @Transactional
    public void deleteProductById(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
        logger.info("Product deleted: {}", id);
    }

    // Helper function (Sama seperti gaya bootcamp kamu)
    private void validate(ProductRequest productRequest) {
        Set<ConstraintViolation<ProductRequest>> violation = validator.validate(productRequest);
        if (!violation.isEmpty()){
            throw new ConstraintViolationException(violation);
        }
    }
}

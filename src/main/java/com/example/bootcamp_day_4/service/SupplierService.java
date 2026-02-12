package com.example.bootcamp_day_4.service;

import com.example.bootcamp_day_4.dto.SupplierRequest;
import com.example.bootcamp_day_4.entity.Supplier;
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
public class SupplierService {
    private final static Logger logger = LoggerFactory.getLogger(SupplierService.class);

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private Validator validator;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @Transactional
    public void createSupplier(SupplierRequest request) {
        validate(request);
        Supplier supplier = new Supplier();
        supplier.setSupplierName(request.getSupplierName());
        supplier.setContact(request.getContact());
        supplier.setAddress(request.getAddress());
        supplierRepository.save(supplier);
    }

    public Supplier getSupplierById(Long id) {
        logger.info("Fetching supplier with ID: {}", id);
        return supplierRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Supplier not found with ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found");
                });
    }

    // Update
    @Transactional
    public void updateSupplierById(Long id, SupplierRequest request) {
        logger.info("Starting to update supplier with ID: {}", id);
        validate(request);

        Supplier supplier = getSupplierById(id);

        supplier.setSupplierName(request.getSupplierName());
        supplier.setContact(request.getContact());
        supplier.setAddress(request.getAddress());

        supplierRepository.save(supplier);
        logger.info("Supplier with ID: {} updated successfully", id);
    }

    // Delete
    @Transactional
    public void deleteSupplierById(Long id) {
        logger.info("Attempting to delete supplier with ID: {}", id);
        Supplier supplier = getSupplierById(id); // Pastikan ada dulu

        supplierRepository.delete(supplier);
        logger.info("Supplier with ID: {} deleted successfully", id);
    }

    private void validate(Object request) {
        Set<ConstraintViolation<Object>> violations = validator.validate(request);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }
}
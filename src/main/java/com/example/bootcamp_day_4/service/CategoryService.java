package com.example.bootcamp_day_4.service;

import com.example.bootcamp_day_4.dto.CategoryRequest;
import com.example.bootcamp_day_4.entity.Category;
import com.example.bootcamp_day_4.entity.Product;
import com.example.bootcamp_day_4.repository.CategoryRepository;
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
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private Validator validator;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Create category
    @Transactional
    public void createCategory(CategoryRequest request) {
        log.info("Starting to create category with name: {}", request.getCategoryName());

        validate(request);
        Category category = new Category();
        category.setCategoryName(request.getCategoryName());
        categoryRepository.save(category);

        log.info("Category created successfully with name: {}", request.getCategoryName());
    }

    // Update category name by id
    @Transactional
    public void updateCategoryById(Long categoryId, CategoryRequest request) {
        log.info("Starting to update category with id: {}", categoryId);

        validate(request);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        category.setCategoryName(request.getCategoryName());
        categoryRepository.save(category);

        log.info("Category updated successfully id: {}", categoryId);
    }

    // Delete category by id
    @Transactional
    public void deleteCategoryById(Long categoryId) {
        log.info("Starting to delete category with id: {}", categoryId);

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        category.setDeletedAt(LocalDateTime.now());
        log.info("Category deleted: {}", categoryId);
    }

    // Helper function
    private void validate(Object request) {
        Set<ConstraintViolation<Object>> violations = validator.validate(request);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }
}
package com.example.bootcamp_day_4.service;

import com.example.bootcamp_day_4.dto.CategoryRequest;
import com.example.bootcamp_day_4.entity.Category;
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

    @Transactional
    public void createCategory(CategoryRequest request) {
        validate(request);
        Category category = new Category();
        category.setCategoryName(request.getCategoryName());
        categoryRepository.save(category);
    }

    @Transactional
    public void updateCategoryById(Long id, CategoryRequest request) {
        validate(request);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        category.setCategoryName(request.getCategoryName());
        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
        categoryRepository.deleteById(id);
    }

    private void validate(Object request) {
        Set<ConstraintViolation<Object>> violations = validator.validate(request);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }
}
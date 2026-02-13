package com.example.bootcamp_day_4.controller;

import com.example.bootcamp_day_4.dto.CategoryRequest;
import com.example.bootcamp_day_4.dto.WebResponse;
import com.example.bootcamp_day_4.entity.Category;
import com.example.bootcamp_day_4.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Create categories
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> create(@RequestBody CategoryRequest request) {
        log.info("Create categories with name:{}", request.getCategoryName());
        categoryService.createCategory(request);
        return WebResponse.<String>builder()
                .message("Success creat category")
                .data("OK")
                .errors(null)
                .build();
    }

    // Get all categories
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<Category>> getAll() {
        log.info("Fetching all categories");
        List<Category> categories = categoryService.getAllCategories();
        return WebResponse.<List<Category>>builder()
                .message("Success get all categories")
                .data(categories)
                .errors(null)
                .build();
    }

    // Update category by id
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> update(@PathVariable Long id, @RequestBody CategoryRequest request) {
        log.info("Update category id={}", id);
        categoryService.updateCategoryById(id, request);
        return WebResponse.<String>builder()
                .message("Success update category")
                .data("OK")
                .errors(null)
                .build();
    }

    // Delete categories by id
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> delete(@PathVariable Long id) {
        log.warn("Deleteing categori id={}", id);
        categoryService.deleteCategoryById(id);
        return WebResponse.<String>builder()
                .message("Success delete category")
                .data("OK")
                .errors(null)
                .build();
    }
}
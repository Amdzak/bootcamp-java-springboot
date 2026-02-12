package com.example.bootcamp_day_4.controller;

import com.example.bootcamp_day_4.dto.SupplierRequest;
import com.example.bootcamp_day_4.dto.WebResponse;
import com.example.bootcamp_day_4.entity.Supplier;
import com.example.bootcamp_day_4.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> create(@RequestBody SupplierRequest request) {
        log.info("Create supplier name={}", request.getSupplierName());
        supplierService.createSupplier(request);
        return WebResponse.<String>builder().message("Success create supplier").data("OK").build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<Supplier>> getAll() {
        log.info("Fetching all suppliers");
        return WebResponse.<List<Supplier>>builder()
                .message("Success get all supplier")
                .data(supplierService.getAllSuppliers())
                .build();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<Supplier> getById(@PathVariable Long id) {
        log.info("Fetcing supplier id={}", id);
        return WebResponse.<Supplier>builder()
                .message("Success Get Supplier")
                .data(supplierService.getSupplierById(id))
                .build();
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> update(@PathVariable Long id, @RequestBody SupplierRequest request) {
        log.info("Updating supplier id={}", id);
        supplierService.updateSupplierById(id, request);
        return WebResponse.<String>builder().message("Success Update Supplier").data("OK").build();
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> delete(@PathVariable Long id) {
        log.warn("Deleting supplier id={}", id);
        supplierService.deleteSupplierById(id);
        return WebResponse.<String>builder().message("Success Delete Supplier").data("OK").build();
    }
}
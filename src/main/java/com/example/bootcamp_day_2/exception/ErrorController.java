package com.example.bootcamp_day_2.exception;

import com.example.bootcamp_day_2.dto.ProductRespond;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

public class ErrorController {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Constraint violation: " + ex.getMessage());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleApiException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ProductRespond.<String>builder().erros(ex.getReason()).build().toString());
    }
}

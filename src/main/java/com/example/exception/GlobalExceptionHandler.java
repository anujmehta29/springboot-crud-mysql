package com.example.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handling validation errors for @Valid annotations
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        // Extracting field errors and their messages
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Print validation errors to console
        logger.error("Validation Errors: {}", errors);

        // Building the structured response
        response.put("timestamp", LocalDateTime.now());  // Adding the current timestamp
        response.put("status", HttpStatus.BAD_REQUEST.value());  // HTTP status code
        response.put("error", "Validation Failed");
        response.put("details", errors);  // Error field details

        // Returning the map of validation errors with BAD_REQUEST status
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handling custom validation exceptions
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleCustomValidationException(ValidationException ex) {
        Map<String, Object> response = new HashMap<>();

        // Log the custom validation exception
        logger.error("Custom Validation Error: {}", ex.getMessage());

        // Building the response with the validation error message
        response.put("timestamp", LocalDateTime.now());  // Adding the current timestamp
        response.put("status", HttpStatus.BAD_REQUEST.value());  // HTTP status code
        response.put("error", "Validation Error");
        response.put("message", ex.getMessage());  // Custom error message

        // Returning the validation error response with BAD_REQUEST status
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Global exception handler for any other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
        Map<String, Object> response = new HashMap<>();

        // Log the global exception details
        logger.error("Global Exception: {}", ex.getMessage(), ex); // Log stack trace

        // Adding additional details to the global error response
        response.put("timestamp", LocalDateTime.now());  // Adding the current timestamp
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());  // HTTP status code
        response.put("error", "Internal Server Error");
        response.put("message", ex.getMessage());  // Error message

        // Returning a generic error message with INTERNAL_SERVER_ERROR status
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

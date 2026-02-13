package com.omerkoc.customer.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.omerkoc.customer.exception.CustomerNotFoundException;

import jakarta.validation.ValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(CustomerNotFoundException ex,
            jakarta.servlet.http.HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                .message(ex.getMessage())
                .errorCode("CUSTOMER_NOT_FOUND")
                .path(request.getRequestURI())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(System.currentTimeMillis())
                .build());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleException(ValidationException ex,
            jakarta.servlet.http.HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder()
                .message(ex.getMessage())
                .errorCode("VALIDATION_ERROR")
                .path(request.getRequestURI())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(System.currentTimeMillis())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex,
            jakarta.servlet.http.HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder()
                .message(ex.getMessage())
                .errorCode("INTERNAL_SERVER_ERROR")
                .path(request.getRequestURI())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(System.currentTimeMillis())
                .build());
    }
}

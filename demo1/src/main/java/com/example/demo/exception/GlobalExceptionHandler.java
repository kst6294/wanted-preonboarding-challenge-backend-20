package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ItemBuyException.class)
    public ResponseEntity<String> handleItemBuyException(ItemBuyException ex) {
       return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
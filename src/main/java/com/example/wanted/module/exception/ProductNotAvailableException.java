package com.example.wanted.module.exception;

public class ProductNotAvailableException extends RuntimeException{
    public ProductNotAvailableException(String message) {
        super(message);
    }
}

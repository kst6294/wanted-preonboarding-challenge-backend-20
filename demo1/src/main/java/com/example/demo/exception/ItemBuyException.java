package com.example.demo.exception;

public class ItemBuyException extends RuntimeException {
    public ItemBuyException(String message) {
        super(message);
    }
}
package com.example.wanted.module.exception;

public class AlreadyOrderException extends RuntimeException{
    public AlreadyOrderException(String message) {
        super(message);
    }
}

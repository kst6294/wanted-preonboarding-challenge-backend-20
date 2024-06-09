package com.example.wanted.module.exception;

public class ErrorDetails {
    private int statusCode;
    private String message;

    public ErrorDetails(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
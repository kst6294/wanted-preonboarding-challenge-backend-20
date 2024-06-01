package com.wanted.preonboarding.global.entity;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private static final String SUCCESS_STATUS = "success";

    private String code;
    private String message;
    private T data;

    public ApiResponse(String code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> createSuccess(T data, String message) {
        return new ApiResponse<>(SUCCESS_STATUS, data, message);
    }

    public static <T> ApiResponse<T> createSuccess(String message) {
        return new ApiResponse<>(SUCCESS_STATUS, null, message);
    }

    public static <T> ApiResponse<T> createError(String code, String message) {
        return new ApiResponse<>(code, null, message);
    }

    public static <T> ApiResponse<T> createError(String code, T data, String message) {
        return new ApiResponse<>(code, data, message);
    }
}


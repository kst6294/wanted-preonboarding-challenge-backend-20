package com.wanted.challenge.global.api;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    // 상태 구분
    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";

    private String code;
    private T data;
    private String message;

    public ApiResponse(String code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> createSuccess(T data, String message) {
        return new ApiResponse<>(SUCCESS_STATUS, data, message);
    }

    public static <T> ApiResponse<T> createSuccessNoContent(String message) {
        return new ApiResponse<>(SUCCESS_STATUS, null, message);
    }

    public static <T> ApiResponse<T> createErrorNoContent(String code, String message) {
        return new ApiResponse<>(code, null, message);
    }

    public static <T> ApiResponse<T> createError(String code, T data, String message) {
        return new ApiResponse<>(code, data, message);
    }


}

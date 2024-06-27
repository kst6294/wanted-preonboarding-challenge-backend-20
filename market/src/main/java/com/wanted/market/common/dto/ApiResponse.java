package com.wanted.market.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ApiResponse<T> {

    private int status;
    private T data;
    private String message;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status(200)
                .data(data)
                .message("Success")
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> deleteSuccess() {
        return ApiResponse.<T>builder()
                .status(200)
                .message("DeleteSuccess")
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(int status, String message) {
        return ApiResponse.<T>builder()
                .status(status)
                .data(null)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}

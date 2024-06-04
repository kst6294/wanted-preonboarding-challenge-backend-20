package com.wanted.market.common.http.dto.response;

public record ApiResponse<T>(
        String message,
        T data
) {
}

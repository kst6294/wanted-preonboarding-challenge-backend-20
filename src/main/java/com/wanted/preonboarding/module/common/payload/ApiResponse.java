package com.wanted.preonboarding.module.common.payload;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {
    private T data;
    private Response response;


    private final static String SUCCESS = "success";

    @Builder
    public ApiResponse(T data, Response response) {
        this.data = data;
        this.response = response;
    }

    public static <T> ApiResponse<T> success(T apiResult, String message) {
        return new ApiResponse<>(apiResult, Response.builder()
                .status(200)
                .message(message != null ? message : SUCCESS)
                .build());
    }

    public static <T> ApiResponse<T> success(T apiResult) {
        return success(apiResult, null);
    }

}

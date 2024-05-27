package com.example.wantedmarketapi.common;

import com.example.wantedmarketapi.exception.GlobalErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class BaseResponse<T> {

    @JsonProperty("isSuccess")
    private Boolean isSuccess;

    private String code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    // 성공한 경우 응답 생성
    public static <T> BaseResponse<T> onSuccess(T data) {
        return new BaseResponse<>(true, "200", "요청에 성공하였습니다.", data);
    }

    public static <T> BaseResponse<T> onSuccess(GlobalErrorCode code, T data) {
        return new BaseResponse<>(
                true, String.valueOf(code.getHttpStatus().value()), code.getMessage(), data);
    }

    // 실패한 경우 응답 생성
    public static <T> BaseResponse<T> onFailure(GlobalErrorCode code, T data) {
        return new BaseResponse<>(
                false, String.valueOf(code.getHttpStatus().value()), code.getMessage(), data);
    }
}
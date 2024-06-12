package com.example.wanted_market.dto;

import com.example.wanted_market.exception.CommonException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;

public record ResponseDto<T>(@JsonIgnore HttpStatus httpStatus,
                             @Nullable T data,
                             @Nullable ExceptionDto error) {
    public static <T> ResponseDto<T> ok(@Nullable final T data) {
        return new ResponseDto<>(HttpStatus.OK, data, null);
    }

    public static <T> ResponseDto<T> fail(final CommonException e) {
        return new ResponseDto<>(e.getErrorCode().getHttpStatus(), null, new ExceptionDto(e.getErrorCode(), e.getMessage()));
    }
}

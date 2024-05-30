package com.wanted.market_api.exception;

import com.wanted.market_api.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler {
@ExceptionHandler(value = { BaseException.class })
protected ResponseEntity<ErrorResponse> handleCustomException(BaseException e) {
    return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}

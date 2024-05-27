package com.wanted.preonboarding.module.exception;

import com.wanted.preonboarding.module.common.payload.ErrorResponse;
import com.wanted.preonboarding.module.exception.product.NotFoundProductException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(NotFoundProductException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundProductException(NotFoundProductException ex) {
        ErrorResponse errorResponse = ErrorResponse.of(ex.getHttpStatus(), ex.getClass().getSimpleName(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }



}

package com.wanted.preonboarding.module.exception;

import com.wanted.preonboarding.module.common.payload.ErrorResponse;
import com.wanted.preonboarding.module.exception.product.NotFoundProductException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String CHECK_LOG_CODE_FORMAT = "Class : {}, Code : {}, Message : {}";

    @ExceptionHandler(value = { ApplicationException.class })
    protected ResponseEntity<?> handleCustomException(ApplicationException e) {

        HttpStatus httpStatus = e.getHttpStatus();
        String errorCode = e.getErrorCode();
        String exceptionClassName = e.getClass().getSimpleName();
        String message = e.getMessage();

        log.error(CHECK_LOG_CODE_FORMAT, exceptionClassName, errorCode, message);

        ErrorResponse response = ErrorResponse.of(httpStatus, exceptionClassName, message);
        return ResponseEntity
                    .status(response.getStatus())
                    .body(response);

    }


    @ExceptionHandler(NotFoundProductException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundProductException(NotFoundProductException ex) {
        ErrorResponse errorResponse = ErrorResponse.of(ex.getHttpStatus(), ex.getClass().getSimpleName(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }



}

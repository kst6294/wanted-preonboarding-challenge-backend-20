package com.wanted.preonboarding.global.exception.handler;

import com.wanted.preonboarding.global.entity.ApiResponse;
import com.wanted.preonboarding.global.exception.entity.RestApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Set;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ApiResponse<?>> handleRestApiException(RestApiException e, HttpServletRequest request) {
        log.error("요청 실패 => 요청 경로: {}, 로그메세지: {}", request.getRequestURL(), e.getLog());

        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(ApiResponse.createError(e.getErrorCode().getCode(), e.getErrorCode().getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> errors = fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + ", " + fieldError.getDefaultMessage())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError("E999", errors, e.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        List<String> errors = constraintViolations.stream()
                .map(constraintViolation -> extractField(constraintViolation.getPropertyPath()) + ", " + constraintViolation.getMessage())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError("E999", errors, e.getMessage()));
    }

    private String extractField(Path path){
        String[] arrays = path.toString().split("[.]");
        int index = arrays.length - 1;
        return arrays[index];
    }
}

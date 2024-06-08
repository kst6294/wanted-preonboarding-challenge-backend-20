package com.wanted.challenge.domain.exception;

import com.wanted.challenge.domain.exception.exception.ItemException;
import com.wanted.challenge.domain.exception.exception.MemberException;
import com.wanted.challenge.domain.exception.exception.TransactionHistoryException;
import com.wanted.challenge.global.api.ApiResponse;
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

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ApiResponse<?>> handleMemberException(MemberException e, HttpServletRequest request) {
        log.warn("요청 실패 - 요청 경로 : {}, 이유 : {}, 로그메시지 : {}", request.getRequestURI(), e.getException().getMessage(), e.getLog());

        return ResponseEntity.status(e.getException().getStatus()).body(ApiResponse.createErrorNoContent(e.getException().getCode(), e.getException().getMessage()));
    }

    @ExceptionHandler(ItemException.class)
    public ResponseEntity<ApiResponse<?>> handleItemException(ItemException e, HttpServletRequest request) {
        log.warn("요청 실패 - 요청 경로 : {}, 이유 : {}, 로그메시지 : {}", request.getRequestURI(), e.getException().getMessage(), e.getLog());

        return ResponseEntity.status(e.getException().getStatus()).body(ApiResponse.createErrorNoContent(e.getException().getCode(), e.getException().getMessage()));
    }

    @ExceptionHandler(TransactionHistoryException.class)
    public ResponseEntity<ApiResponse<?>> handleTransactionHistoryException(TransactionHistoryException e, HttpServletRequest request) {
        log.warn("요청 실패 - 요청 경로 : {}, 이유 : {}, 로그메시지 : {}", request.getRequestURI(), e.getException().getMessage(), e.getLog());

        return ResponseEntity.status(e.getException().getStatus()).body(ApiResponse.createErrorNoContent(e.getException().getCode(), e.getException().getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> errors = fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + ", " + fieldError.getDefaultMessage())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError("9999", errors, e.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        List<String> errors = constraintViolations.stream()
                .map(constraintViolation -> extractField(constraintViolation.getPropertyPath()) + ", " + constraintViolation.getMessage())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError("9999", errors, e.getMessage()));
    }

    private String extractField(Path path){
        String[] arrays = path.toString().split("[.]");
        int index = arrays.length - 1;
        return arrays[index];
    }
}

package org.example.preonboarding.common.controllerAdvices;

import org.example.preonboarding.common.enums.ResultCode;
import org.example.preonboarding.common.payload.ApiError;
import org.example.preonboarding.member.exception.WithdrawException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WithdrawException.class)
    public ResponseEntity<?> handleWithdrawException(WithdrawException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiError.builder()
                        .resultCode(ResultCode.WITHDRAW_FAIL)
                        .data(ex.getMessage())
                        .build()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> bindException(BindException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiError.builder()
                        .resultCode(ResultCode.FAIL)
                        .data(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                        .build()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> bindException(SQLIntegrityConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiError.builder()
                        .resultCode(ResultCode.FAIL)
                        .data(ex.getMessage())
                        .build()
        );
    }

}

package org.example.preonboarding.common.controllerAdvices;

import org.example.preonboarding.common.enums.ResultCode;
import org.example.preonboarding.common.payload.ApiError;
import org.example.preonboarding.member.exception.WithdrawException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

}

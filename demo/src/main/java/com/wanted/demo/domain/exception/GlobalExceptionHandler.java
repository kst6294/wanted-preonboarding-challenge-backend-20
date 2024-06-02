package com.wanted.demo.domain.exception;

import com.wanted.demo.domain.exception.exception.UserException;
import com.wanted.demo.global.util.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiResponse<?>> handleUserException(UserException e, HttpServletRequest request){

        log.error("요청 경로 : {}, 실패 이유 : {}, 로그 : {}",request.getRequestURI(),e.getException().getMessage(),e.getLog());

        return ResponseEntity.status(e.getException().getHttpStatus()).body(ApiResponse.createErrorNoContent(e.getException().getCode(),e.getException().getMessage()));
    }
}

package com.chaewon.wanted.base.exception;

import com.chaewon.wanted.base.jwt.InvalidTokenException;
import com.chaewon.wanted.common.ResponseDto;
import com.chaewon.wanted.domain.member.exception.InvalidRefreshTokenException;
import com.chaewon.wanted.domain.member.exception.MemberNotFoundException;
import com.chaewon.wanted.domain.member.exception.MultipleLoginException;
import com.chaewon.wanted.domain.product.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MultipleLoginException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ResponseDto> handleMultipleLoginException(MultipleLoginException e) {
        return ResponseDto.of(HttpStatus.CONFLICT, e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Invalid input");

        return ResponseDto.of(HttpStatus.BAD_REQUEST, errorMessage);
    }
    @ExceptionHandler(InvalidRefreshTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDto> handleInvalidRefreshTokenException(InvalidRefreshTokenException e) {
        return ResponseDto.of(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ResponseDto> handleInvalidTokenException(InvalidTokenException e) {
        return ResponseDto.of(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDto> handleRuntimeException(RuntimeException e) {
        return ResponseDto.of(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");
    }

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseDto> handleMemberNotFoundException(MemberNotFoundException e) {
        return ResponseDto.of(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseDto> handleProductNotFoundException(ProductNotFoundException e) {
        return ResponseDto.of(HttpStatus.NOT_FOUND, e.getMessage());
    }

}

package com.example.wantedmarketapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode {
    CREATED(HttpStatus.CREATED, "요청 성공 및 리소스 생성됨"),
    UPDATED(HttpStatus.ACCEPTED, "요청 성공 및 리소스 수정됨"),
    DELETED(HttpStatus.NO_CONTENT, "요청 성공 및 리소스 삭제됨");

    private final HttpStatus httpStatus;
    private final String message;
}
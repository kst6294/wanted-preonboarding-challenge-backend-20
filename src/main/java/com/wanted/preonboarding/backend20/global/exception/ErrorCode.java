package com.wanted.preonboarding.backend20.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),

    AUTHENTICATION_ERROR(HttpStatus.UNAUTHORIZED, "회원만 가능한 기능입니다."),
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "해당 작업을 수행할 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 데이터를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}

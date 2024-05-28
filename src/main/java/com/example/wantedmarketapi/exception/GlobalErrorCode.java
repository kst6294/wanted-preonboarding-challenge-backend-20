package com.example.wantedmarketapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode {
    CREATED(HttpStatus.CREATED, "요청 성공 및 리소스 생성됨"),
    UPDATED(HttpStatus.ACCEPTED, "요청 성공 및 리소스 수정됨"),
    DELETED(HttpStatus.NO_CONTENT, "요청 성공 및 리소스 삭제됨"),

    // member
    NOT_VALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호는 영문, 숫자, 특수문자를 포함한 9~16글자여야 합니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    AUTHENTICATION_REQUIRED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "등록된 사용자가 없습니다."),
    EXIST_MEMBER(HttpStatus.CONFLICT, "등록된 이메일입니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
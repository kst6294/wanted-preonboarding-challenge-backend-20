package com.wanted.demo.domain.exception.responseCode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserExceptionResponseCode {

    //409 Conflict
    EXISTS_USER(HttpStatus.CONFLICT, "U-001", "이미 존재하는 멤버입니다."),
    NOT_MATCH_PASSWORD(HttpStatus.CONFLICT,"U-002","비밀번호가 틀립니다."),
    //404 Not Found
    NOT_EXISTS_USER(HttpStatus.NOT_FOUND, "U-003", "멤버가 존재하지 않습니다."),
    NOT_EXISTS_SESSION(HttpStatus.NOT_FOUND, "U-004", "멤버가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    UserExceptionResponseCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}

package com.wanted.preonboarding.global.exception.errorCode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "U001", "사용자가 존재하지 않습니다"),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "U002", "이메일이 이미 존재합니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "U003", "비밀번호가 일치하지 않습니다.")

    ;

    private HttpStatus httpStatus;
    private String code;
    private String message;

    UserErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

}

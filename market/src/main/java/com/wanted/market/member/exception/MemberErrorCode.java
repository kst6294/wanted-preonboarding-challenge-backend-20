package com.wanted.market.member.exception;

import com.wanted.market.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    NOT_SELLER(HttpStatus.CONFLICT, "판매자가 아닙니다.");

    private final HttpStatus httpStatus;
    private final String message;

    MemberErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

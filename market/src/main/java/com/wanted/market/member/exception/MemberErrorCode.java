package com.wanted.market.member.exception;


import com.wanted.market.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    NOT_SELLER(HttpStatus.FORBIDDEN, "판매자만 접근 가능합니다."),
    EXISTS_MEMBER(HttpStatus.CONFLICT, "이미 가입되어 있는 이메일입니다."),
    NOT_MATCH_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    NOT_BUYER(HttpStatus.FORBIDDEN, "구매자만 접근 가능합니다."),
    NOT_EXIST_MEMBER(HttpStatus.FORBIDDEN, "사용자가 아닙니다."),
    NOT_SELLER_OF_PRODUCT(HttpStatus.CONFLICT, "해당 상품의 판매자가 아닙니다.");

    private HttpStatus httpStatus;
    private String message;

    MemberErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

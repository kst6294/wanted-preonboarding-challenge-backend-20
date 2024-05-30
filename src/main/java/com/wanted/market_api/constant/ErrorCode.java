package com.wanted.market_api.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNAUTHORIZED_JWT(HttpStatus.UNAUTHORIZED, "ACC-001", "인증 실패"),
    INSUFFICIENT_AUTHORIZATION(HttpStatus.FORBIDDEN, "ACC-002", "필요한 권한이 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ACC-003", "사용자를 찾을 수 없습니다."),
    REPEATED_USER(HttpStatus.BAD_REQUEST, "ACC-004", "중복된 유저네임이 존재합니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "ACC-005", "올바르지 않은 비밀번호입니다."),
    JWT_REQUIRED(HttpStatus.FORBIDDEN, "ACCOUNT-006", "해당 API는 JWT 인증이 필요합니다."),

    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT-001", "제품을 찾을 수 없습니다."),

    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER-001", "주문을 찾을 수 없습니다.")

    ;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}

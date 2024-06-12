package com.example.wanted_market.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /** 400 **/
    // GlobalExceptionHandler
    UNSUPPORTED_MEDIA_TYPE(40000, HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원되지 않는 미디어 타입입니다."),
    NOT_END_POINT(40001, HttpStatus.NOT_FOUND, "존재하지 않는 엔드포인트입니다."),
    INVALID_ARGUMENT(40002, HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    METHOD_NOT_ALLOWED(40003, HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 HTTP 메소드입니다."),
    METHOD_ARGUMENT_TYPE_MISMATCH(40004, HttpStatus.BAD_REQUEST, "메소드 인수의 타입이 잘못되었습니다."),
    MISSING_REQUEST_PARAMETER(40005, HttpStatus.BAD_REQUEST, "요청 파라미터가 누락되었습니다."),
    DUPLICATE_RESOURCE(40006, HttpStatus.CONFLICT, "리소스가 이미 존재합니다."),

    // Auth
    EMAIL_ALREADY_EXISTED(40101, HttpStatus.CONFLICT, "이미 존재하는 이메일 입니다."),
    EMAIL_NOT_EXIST(40102, HttpStatus.NOT_FOUND, "존재하지 않는 이메일입니다."),
    PASSWORD_NOT_MATCH(40103, HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    LOGIN_REQUIRED(40104, HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    USER_NOT_FOUND(40105, HttpStatus.NOT_FOUND, "해당 사용자가 존재하지 않습니다."),

    // Product
    PRODUCT_NOT_FOUND(40201, HttpStatus.NOT_FOUND, "해당 제품이 존재하지 않습니다."),

    // Order
    ORDER_NOT_FOUND(40301, HttpStatus.NOT_FOUND, "해당 주문이 존재하지 않습니다."),
    PRODUCT_OWNER_CANNOT_PURCHASE(40302, HttpStatus.BAD_REQUEST, "본인이 등록한 제품은 구매할 수 없습니다."),
    PRODUCT_ALREADY_RESERVED(40303, HttpStatus.BAD_REQUEST, "해당 제품은 현재 예약 중입니다."),
    PRODUCT_ALREADY_SOLD(40304, HttpStatus.BAD_REQUEST, "해당 제품은 이미 판매 완료되었습니다."),
    ORDER_ALREADY_COMPLETED(40305, HttpStatus.BAD_REQUEST, "이미 완료된 주문입니다."),
    ONLY_SELLER_CAN_APPROVE(40306, HttpStatus.UNAUTHORIZED, "판매자만 판매 승인할 수 있습니다."),


    /** 500 **/
    // GlobalExceptionHandler
    SERVER_ERROR(50000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;
}

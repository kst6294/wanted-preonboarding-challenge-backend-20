package com.exception_study.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not founded"),
    USER_ALREADY_EXIST(HttpStatus.CONFLICT,"User already exists"),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND,"Product not founded"),
    PRODUCT_SOLD_OUT(HttpStatus.CONFLICT,"Product sold out"),
    PRODUCT_ALREADY_RESERVED(HttpStatus.CONFLICT,"Already reserved product"),
    PRODUCT_ORDER_ALREADY_CONFIRMED(HttpStatus.CONFLICT,"Already confirmed order"),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND,"ORDER not founded"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Invalid password"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "User has invalid permission"),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Database error occurs");

    private final HttpStatus status;
    private final String message;
}

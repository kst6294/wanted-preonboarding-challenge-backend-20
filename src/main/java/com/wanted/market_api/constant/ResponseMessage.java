package com.wanted.market_api.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS(200, "SUCCESS"),
    REGISTRATION_SUCCESS(200, "회원가입에 성공하였습니다."),
    ORDER_SUCCESS(200, "주문 생성에 성공하였습니다.");

    private final int code;
    private final String message;
}


package org.example.wantedmarket.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND("등록된 사용자가 없습니다."),
    USER_NOT_SELLER("해당 제품의 판매자가 아닙니다."),
    USERNAME_ALREADY_IN_USE("이미 사용중인 이름입니다."),

    PRODUCT_NOT_FOUND("등록된 제품이 없습니다."),
    PRODUCT_SOLD_OUT("해당 제품은 판매 완료되었습니다."),

    ORDER_NOT_FOUND("등록된 주문이 없습니다."),
    ORDER_COMPLETED("이미 완료된 주문입니다."),
    ORDER_MY_PRODUCT_NOT_ALLOWED("본인이 등록한 제품은 주문할 수 없습니다.");

    private final String errorMessage;
}

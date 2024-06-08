package com.wanted.challenge.domain.exception.info;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ItemExceptionInfo {
    NOT_FOUND_ITEM(HttpStatus.NOT_FOUND, "ITEM-001", "상품이 존재하지 않습니다."),
    NOT_ENOUGH_ITEM_QUANTITY(HttpStatus.BAD_REQUEST, "ITEM-002", "상품의 재고가 부족합니다."),
    ALREADY_PURCHASE_ITEM(HttpStatus.BAD_REQUEST, "ITEM-003", "이미 구매한 상품입니다."),
    DONT_PURCHASE_SELF_ITEM(HttpStatus.BAD_REQUEST, "ITEM-004", "본인이 등록한 물건은 구매할 수 없습니다.");


    private HttpStatus status;
    private String code;
    private String message;

    ItemExceptionInfo(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}

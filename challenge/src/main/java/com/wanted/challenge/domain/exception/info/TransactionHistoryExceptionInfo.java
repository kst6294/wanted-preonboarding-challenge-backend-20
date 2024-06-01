package com.wanted.challenge.domain.exception.info;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TransactionHistoryExceptionInfo {
    NOT_FOUNT_HISTORY(HttpStatus.BAD_REQUEST, "3001", "거래 기록을 찾을 수 없습니다."),
    NOT_MATCH_ITEM_SELLER(HttpStatus.BAD_REQUEST, "3002", "상품 판매자가 아닙니다."),
    ALREADY_SALE_CONFIRM(HttpStatus.BAD_REQUEST, "3003", "이미 판매 승인된 상품입니다."),
    NOT_MATCH_ITEM_BUYER(HttpStatus.BAD_REQUEST, "3004", "상품 구매자가 아닙니다."),
    ALREADY_PURCHASE_CONFIRM(HttpStatus.BAD_REQUEST, "3005", "이미 구매 확인된 상품입니다."),
    NOT_SALE_CONFIRM(HttpStatus.BAD_REQUEST, "3006", "판매 승인되지 않은 상품입니다.");


    private HttpStatus status;
    private String code;
    private String message;

    TransactionHistoryExceptionInfo(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}

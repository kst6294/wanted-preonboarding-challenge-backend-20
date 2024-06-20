package com.wanted.market.order.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum OrderStatus {

    TRADING("거래중"),
    TRADE_APPROVE("거래완료"),
    TRADE_CANCEL("거래취소");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }
}

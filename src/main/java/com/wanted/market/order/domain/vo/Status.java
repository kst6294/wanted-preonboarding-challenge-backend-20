package com.wanted.market.order.domain.vo;

public enum Status {
    REQUESTED("구매요청"), CONFIRMED("진행중"), FINISHED("구매확정");

    private final String value;

    Status(String value) {
        this.value = value;
    }
}

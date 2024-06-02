package com.wanted.market.order.domain.vo;

public enum Status {
    ON_GOING("예약중"), CONFIRMED("구매확정");

    private final String value;

    Status(String value) {
        this.value = value;
    }
}

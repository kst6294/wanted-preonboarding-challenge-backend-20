package com.wanted.market.product.domain.vo;

public enum Status {
    PREPARING("준비중"), ON_SALE("판매중"), RESERVED("예약중"), SOLD("완료");

    private final String value;

    Status(String value) {
        this.value = value;
    }
}

package com.wanted.market.product.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductStatus {
    ON_SALE("판매중"),
    RESERVED("예약중"),
    COMPLETED("완료");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}

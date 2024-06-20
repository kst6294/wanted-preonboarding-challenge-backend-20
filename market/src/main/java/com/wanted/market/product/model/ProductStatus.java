package com.wanted.market.product.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ProductStatus {
    ON_SALE("판매중"),
    RESERVED("예약중"),
    COMPLETED("판매완료");

    private final String status;

    ProductStatus(String status) {
        this.status = status;
    }

}

package com.market.wanted.product.entity;

public enum ProductStatus {
    SALE("판매중"),
    RESERVATION("예약중"),
    SOLD_OUT("완료");

    ProductStatus(String description) {
    }
}

package com.example.wanted.product.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductSellingStatus {
    SELLING("판매 중"),
    RESERVATION("예약 중"),
    STOP("판매 중지");

    private final String text;
}

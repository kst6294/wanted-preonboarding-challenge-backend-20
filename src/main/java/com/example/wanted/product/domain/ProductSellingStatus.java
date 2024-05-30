package com.example.wanted.product.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductSellingStatus {
    SELLING("판매 중"),
    RESERVATION("예약 중"),
    COMPLETE("완료");

    private final String text;
}

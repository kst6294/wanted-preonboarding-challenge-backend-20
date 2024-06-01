package com.example.wanted.order.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    REQUEST("구매 요청"),
    APPROVAL("판매 승인"),
    PURCHASE_CONFIRMATION("구매 확정");

    private final String text;

    public static List<OrderStatus> notConfirmationStatus() {
        return List.of(REQUEST, APPROVAL);
    }
}

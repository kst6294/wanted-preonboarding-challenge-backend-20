package com.market.wanted.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    SALES_APPROVAL_WAITING("판매승인 대기"),
    PURCHASE_CONFIRMATION_WAITING("구매확정 대기"),
    COMPLETE("완료");

    private final String name;
}

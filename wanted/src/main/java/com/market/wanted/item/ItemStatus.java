package com.market.wanted.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemStatus {
    SALE("판매중"),
    RESERVATION("예약중"),
    COMPLETE("완료");

    private final String name;
}

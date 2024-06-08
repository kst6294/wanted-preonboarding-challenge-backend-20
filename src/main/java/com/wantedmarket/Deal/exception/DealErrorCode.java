package com.wantedmarket.Deal.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DealErrorCode {
    SAME_MEMBER("본인이 등록한 제품은 구매할 수 없습니다"),
    IN_PROGRESS_OR_SOLD_OUT("구매할 수 없습니다"),
    INVALID_DEAL("유효하지 않은 거래입니다"),
    NOT_SELLER("판매자가 아닙니다"),
    INVALID_STATUS("예약되지 않았거나 이미 판매된 제품입니다");
    private final String description;
}

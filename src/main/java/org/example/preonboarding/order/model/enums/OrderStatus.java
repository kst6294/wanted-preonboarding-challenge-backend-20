package org.example.preonboarding.order.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    INIT("주문생성"), // 예약 개념
    CANCELED("주문취소"),
    PAYMENT_COMPLETED("결제완료"),
    PAYMENT_FAILED("결제실패"),
    APPROVED("주문승인"),
    COMPLETED("구매확정");

    private final String text;

}

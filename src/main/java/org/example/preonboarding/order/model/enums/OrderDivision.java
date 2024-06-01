package org.example.preonboarding.order.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum OrderDivision {

    SELLER("판매자"), // 예약 개념
    BUYER("구매자"),
    ALL("전체"),
    ;

    private final String text;

    public static List<OrderDivision> forAll() {
        return List.of(SELLER, BUYER);
    }
}

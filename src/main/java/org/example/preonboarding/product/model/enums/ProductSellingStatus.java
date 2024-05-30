package org.example.preonboarding.product.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ProductSellingStatus {

    SELLING("판매중"),
    RESERVED("예약중"),
    SOLD_OUT("완료"),
    DELETED("제품삭제")
    ;

    private final String text;

    public static List<ProductSellingStatus> forDisplay() {
        return List.of(SELLING);
    }

}
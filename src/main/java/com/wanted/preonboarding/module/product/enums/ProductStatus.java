package com.wanted.preonboarding.module.product.enums;

import com.wanted.preonboarding.module.common.enums.EnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus implements EnumType {

    ON_STOCK("판매중"),
    BOOKING("예약중"),
    OUT_OF_STOCK("품절");


    private String displayName;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getDescription() {
        return String.format("제품의 판매 상태 %s", displayName);
    }


    public boolean isOnStock() {
        return this == ON_STOCK;
    }
}

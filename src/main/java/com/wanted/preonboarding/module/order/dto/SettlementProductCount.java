package com.wanted.preonboarding.module.order.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SettlementProductCount {

    private int quantity;
    private long orderedOrCompletedProductCount;

    @QueryProjection
    public SettlementProductCount(int quantity, long orderedOrCompletedProductCount) {
        this.quantity = quantity;
        this.orderedOrCompletedProductCount = orderedOrCompletedProductCount;
    }

    public boolean isAllSettlementStatus() {
        return quantity == 0 && orderedOrCompletedProductCount == 0;
    }

}

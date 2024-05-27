package com.wanted.preonboarding.module.order.enums;

import com.wanted.preonboarding.module.common.enums.EnumType;

public enum OrderStatus implements EnumType {

    ORDERED,
    COMPLETED,
    SETTLEMENT
    ;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getDescription() {
        return String.format("Order status: %s", name());
    }

    public boolean isCompleted(){
        return this.equals(COMPLETED);
    }

    public boolean isSettlement(){
        return this.equals(SETTLEMENT);
    }
}

package com.wanted.preonboarding.module.order.enums;

import com.wanted.preonboarding.module.common.enums.EnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus implements EnumType {

    ORDERED("판매중"),
    COMPLETED("예약중"),
    SETTLEMENT("완료")
    ;

    private final String displayName;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getDescription() {
        return String.format(displayName);
    }

    public boolean isCompleted(){
        return this.equals(COMPLETED);
    }

    public boolean isSettlement(){
        return this.equals(SETTLEMENT);
    }
}

package com.wanted.preonboarding.module.order.enums;

import com.wanted.preonboarding.module.common.enums.EnumType;

public enum UserRole implements EnumType {
    SELLER,
    BUYER

    ;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getDescription() {
        return "판매자 / 구매자";
    }

    public boolean isSeller(){
        return this.equals(SELLER);
    }

}

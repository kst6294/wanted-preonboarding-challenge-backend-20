package com.wanted.preonboarding.module.user.enums;

import com.wanted.preonboarding.module.common.enums.EnumType;

public enum MemberShip implements EnumType {
    GUEST,
    NORMAL,
    ;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getDescription() {
        return "USER MEMBERSHIP";
    }
}

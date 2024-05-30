package com.wanted.preonboarding.module.user.enums;

import com.wanted.preonboarding.module.common.enums.EnumType;

import java.util.Arrays;

public enum MemberShip implements EnumType {
    GUEST,
    NORMAL,
    ;


    public static MemberShip of(String code) {
        return Arrays.stream(MemberShip.values())
                .filter(r -> r.name().equals(code))
                .findAny()
                .orElse(GUEST);
    }


    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getDescription() {
        return "USER MEMBERSHIP";
    }
}

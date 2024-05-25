package com.wanted.preonboarding.module.common.enums;

public enum Yn implements EnumType {
    Y,
    N;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getDescription() {
        return String.format("Y means YES, N meas NO");
    }
}

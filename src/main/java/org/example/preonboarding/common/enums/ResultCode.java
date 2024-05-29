package org.example.preonboarding.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.preonboarding.common.enums.interfaces.ResultCodeAware;

@Getter
@AllArgsConstructor
public enum ResultCode implements ResultCodeAware {
    SUCCESS(0, "success"),
    SIGNUP_SUCCESS(1, "signup success"),
    LOGIN_SUCCESS(2, "login success"),
    REFRESH_SUCCESS(3, "refresh token success"),
    WITHDRAW_SUCCESS(4, "withdraw success"),
    WITHDRAW_FAIL(5, "withdraw fail"),
    FAIL(99, "99 fail"),
    ;
    public final int code;
    public final String message;

}

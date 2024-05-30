package com.wanted.market_api.utils;

import com.wanted.market_api.constant.ErrorCode;
import com.wanted.market_api.exception.BaseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
    public static Long checkAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().equals("anonymousUser")) {
            throw new BaseException(ErrorCode.JWT_REQUIRED);
        } else {
            return Long.parseLong(authentication.getName());
        }
    }

    public static Long checkNonRequiredAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().equals("anonymousUser")) {
            return null;
        } else {
            return Long.parseLong(authentication.getName());
        }
    }
}

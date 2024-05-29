package com.wanted.preonboarding.module.utils;

import com.wanted.preonboarding.auth.core.UserPrincipal;
import com.wanted.preonboarding.module.exception.auth.UnAuthorizationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    private static final String UN_AUTH_ERR_MSG= "No authentication information available";

    private static final String GUEST= "guest";

    public static String currentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal)) {
            throw new UnAuthorizationException(UN_AUTH_ERR_MSG);
        }
        return ((UserPrincipal) authentication.getPrincipal()).getEmail();
    }

    public static String currentUserEmailForLogging() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal)) {
            return GUEST;
        }
        return ((UserPrincipal) authentication.getPrincipal()).getEmail();
    }

}

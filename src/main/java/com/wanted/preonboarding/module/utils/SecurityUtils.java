package com.wanted.preonboarding.module.utils;

import com.wanted.preonboarding.auth.core.UserPrincipal;
import com.wanted.preonboarding.module.exception.auth.UnAuthorizationException;
import com.wanted.preonboarding.module.user.enums.MemberShip;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

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


    public static MemberShip getAuthorization(Collection<? extends GrantedAuthority> authorities){
        return authorities.stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .map(MemberShip::of)
                .orElse(MemberShip.GUEST);

    }

}

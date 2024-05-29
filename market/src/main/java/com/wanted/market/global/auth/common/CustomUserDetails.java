package com.wanted.market.global.auth.common;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {

    private long memberNo;

    public long getMemberNo() {
        return memberNo;
    }

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, long memberNo) {
        super(username, password, authorities);
        this.memberNo = memberNo;
    }
}

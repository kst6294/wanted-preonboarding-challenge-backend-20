package com.wanted.preonboarding.auth.core;

import com.wanted.preonboarding.module.user.core.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


@Getter
@Builder
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private String email;
    private String phoneNumber;
    private String passwordHash;
    private final Collection<GrantedAuthority> authorities;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(authorities);
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }


    public static UserPrincipal toUserPrincipal(UserInfo userInfo) {
        return UserPrincipal.builder()
                .email(userInfo.getEmail())
                .phoneNumber(userInfo.getPhoneNumber())
                .passwordHash(userInfo.getPasswordHash())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(userInfo.getMemberShip().name())))
                .build();
    }

}

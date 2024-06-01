package com.wanted.preonboarding.domain.user.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomUserDetails implements UserDetails {

    private String id;
    private String email;
    private String nickname;
    private Collection<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return id;
    }

    public static CustomUserDetails of(User user) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getType().name());
        return CustomUserDetails.builder()
                .id(user.getId().toString())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .authorities(Collections.singletonList(grantedAuthority))
                .build();

    }
}
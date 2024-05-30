package com.wanted.preonboarding.auth.service;

import com.wanted.preonboarding.auth.core.UserPrincipal;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.core.UserInfo;
import com.wanted.preonboarding.module.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CustomDetailUserService implements UserDetailsService{

    private final UserFindService userFindService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        BaseUserInfo userInfo = fetchUserInfo(email);
        return UserPrincipal.builder()
                    .email(userInfo.getEmail())
                    .phoneNumber(userInfo.getPhoneNumber())
                    .passwordHash(userInfo.getPasswordHash())
                    .authorities(Collections.singletonList(new SimpleGrantedAuthority(userInfo.getMemberShip().name())))
                    .build();
    }

    private BaseUserInfo fetchUserInfo(String email){
        return userFindService.fetchUserInfo(email);
    }

}

package com.wanted.preonboarding.auth.service;

import com.wanted.preonboarding.auth.AuthTokenProvider;
import com.wanted.preonboarding.auth.core.AuthToken;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AuthTokenGenerateServiceImpl implements AuthTokenGenerateService{

    private final UserDetailsService userDetailsService;
    private final AuthTokenProvider authTokenProvider;
    private final TokenQueryService tokenQueryService;

    @Override
    public AuthToken generateToken(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        AuthToken authToken = authTokenProvider.createAuthToken(userDetails, false);
        AuthToken refreshAuthToken = authTokenProvider.createAuthToken(userDetails, true);
        tokenQueryService.saveToken(refreshAuthToken);

        return authToken;
    }

}

package com.wanted.preonboarding.auth.service;

import com.wanted.preonboarding.auth.AuthTokenProvider;
import com.wanted.preonboarding.auth.core.AuthToken;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AuthTokenGenerateServiceImpl implements AuthTokenGenerateService{

    private final UserFindService userFindService;
    private final AuthTokenProvider authTokenProvider;
    private final TokenQueryService tokenQueryService;

    @Override
    public AuthToken generateToken(String email) {
        BaseUserInfo baseUserInfo = userFindService.fetchUserInfo(email);
        AuthToken authToken = authTokenProvider.createAuthToken(baseUserInfo, false);
        AuthToken refreshAuthToken = authTokenProvider.createAuthToken(baseUserInfo, true);

        tokenQueryService.saveToken(refreshAuthToken);

        return authToken;
    }

}

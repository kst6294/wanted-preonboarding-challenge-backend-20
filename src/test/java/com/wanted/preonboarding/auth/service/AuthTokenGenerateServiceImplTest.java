package com.wanted.preonboarding.auth.service;

import com.wanted.preonboarding.auth.AuthTokenProvider;
import com.wanted.preonboarding.auth.core.AuthToken;
import com.wanted.preonboarding.auth.core.UserPrincipal;
import com.wanted.preonboarding.data.auth.AuthModuleHelper;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.service.UserFindService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthTokenGenerateServiceImplTest {

    @InjectMocks
    private AuthTokenGenerateServiceImpl authTokenGenerateService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private AuthTokenProvider authTokenProvider;

    @Mock
    private TokenQueryService tokenQueryService;

    @Test
    @DisplayName("토큰 생성 테스트")
    void generateToken() {

        BaseUserInfo baseUserInfo = AuthModuleHelper.toBaseUserInfo();
        String email = baseUserInfo.getEmail();
        UserPrincipal userPrincipal = AuthModuleHelper.toUserPrincipal(baseUserInfo);

        AuthToken accessToken = mock(AuthToken.class);
        AuthToken refreshToken = mock(AuthToken.class);

        when(userDetailsService.loadUserByUsername(email)).thenReturn(userPrincipal);
        when(authTokenProvider.createAuthToken(userPrincipal, false)).thenReturn(accessToken);
        when(authTokenProvider.createAuthToken(userPrincipal, true)).thenReturn(refreshToken);


        AuthToken generatedToken = authTokenGenerateService.generateToken(email);

        assertNotNull(generatedToken);
        assertEquals(accessToken, generatedToken);
        verify(userDetailsService, times(1)).loadUserByUsername(email);
        verify(authTokenProvider, times(1)).createAuthToken(userPrincipal, false);
        verify(authTokenProvider, times(1)).createAuthToken(userPrincipal, true);
        verify(tokenQueryService, times(1)).saveToken(refreshToken);
    }


}
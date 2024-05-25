package com.wanted.preonboarding.auth.service;

import com.wanted.preonboarding.auth.AuthTokenProvider;
import com.wanted.preonboarding.auth.core.AuthToken;
import com.wanted.preonboarding.data.auth.AuthModuleHelper;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.service.UserFindService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthTokenGenerateServiceImplTest {

    @InjectMocks
    private AuthTokenGenerateServiceImpl authTokenGenerateService;

    @Mock
    private UserFindService userFindService;

    @Mock
    private AuthTokenProvider authTokenProvider;

    @Mock
    private TokenQueryService tokenQueryService;

    @Test
    @DisplayName("토큰 생성 테스트")
    void generateToken() {

        BaseUserInfo baseUserInfo = AuthModuleHelper.toBaseUserInfo();
        String email = baseUserInfo.getEmail();

        AuthToken accessToken = mock(AuthToken.class);
        AuthToken refreshToken = mock(AuthToken.class);

        when(userFindService.fetchUserInfo(email)).thenReturn(baseUserInfo);
        when(authTokenProvider.createAuthToken(baseUserInfo, false)).thenReturn(accessToken);
        when(authTokenProvider.createAuthToken(baseUserInfo, true)).thenReturn(refreshToken);


        AuthToken generatedToken = authTokenGenerateService.generateToken(email);

        assertNotNull(generatedToken);
        assertEquals(accessToken, generatedToken);
        verify(userFindService, times(1)).fetchUserInfo(email);
        verify(authTokenProvider, times(1)).createAuthToken(baseUserInfo, false);
        verify(authTokenProvider, times(1)).createAuthToken(baseUserInfo, true);
        verify(tokenQueryService, times(1)).saveToken(refreshToken);
    }


}
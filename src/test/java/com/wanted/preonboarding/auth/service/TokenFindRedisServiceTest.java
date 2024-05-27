package com.wanted.preonboarding.auth.service;

import com.wanted.preonboarding.auth.core.AuthToken;
import com.wanted.preonboarding.auth.core.JwtAuthToken;
import com.wanted.preonboarding.data.auth.AuthModuleHelper;
import com.wanted.preonboarding.document.utils.RedisServiceTest;
import com.wanted.preonboarding.module.common.enums.RedisKey;
import com.wanted.preonboarding.module.utils.JsonUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class TokenFindRedisServiceTest extends RedisServiceTest {


    @InjectMocks
    private TokenFindRedisService tokenFetchRedisService;

    @Test
    @DisplayName("토큰 조회 테스트 - 존재하는 경우")
    void fetchToken_WhenTokenExists() {
        JwtAuthToken jwtAuthToken = AuthModuleHelper.toJwtAuthToken();
        String key = RedisKey.REFRESH_TOKEN.generateKey(jwtAuthToken.getSubject());

        String tokenJson = JsonUtils.toJson(jwtAuthToken);
        when(valueOperations.get(key)).thenReturn(tokenJson);

        Optional<AuthToken> fetchedToken = tokenFetchRedisService.fetchToken(jwtAuthToken.getSubject());

        assertTrue(fetchedToken.isPresent());

        verify(valueOperations, times(1)).get(eq(key));
    }

    @Test
    @DisplayName("토큰 조회 테스트 - 존재하지 않는 경우")
    void fetchToken_WhenTokenDoesNotExist() {
        JwtAuthToken jwtAuthToken = AuthModuleHelper.toJwtAuthToken();
        String key = RedisKey.REFRESH_TOKEN.generateKey(jwtAuthToken.getSubject());

        when(valueOperations.get(key)).thenReturn(null);
        Optional<AuthToken> fetchedToken = tokenFetchRedisService.fetchToken(jwtAuthToken.getSubject());
        assertTrue(fetchedToken.isEmpty());

        verify(valueOperations, times(1)).get(eq(key));
    }

}
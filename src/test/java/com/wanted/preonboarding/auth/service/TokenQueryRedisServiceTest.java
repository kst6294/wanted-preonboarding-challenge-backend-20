package com.wanted.preonboarding.auth.service;

import com.wanted.preonboarding.auth.core.JwtAuthToken;
import com.wanted.preonboarding.data.auth.AuthModuleHelper;
import com.wanted.preonboarding.document.utils.RedisServiceTest;
import com.wanted.preonboarding.module.common.enums.RedisKey;
import com.wanted.preonboarding.module.utils.JsonUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.mockito.Mockito.*;


class TokenQueryRedisServiceTest extends RedisServiceTest {

    @InjectMocks
    private TokenQueryRedisService tokenQueryRedisService;

    @Test
    @DisplayName("토큰 저장 테스트")
    void saveToken() {
        JwtAuthToken token = AuthModuleHelper.toJwtAuthToken();
        String tokenJson = JsonUtils.toJson(token);
        String key = tokenQueryRedisService.generateKey(RedisKey.REFRESH_TOKEN, token.getSubject());
        tokenQueryRedisService.saveToken(token);
        verify(valueOperations, times(1)).set(eq(key), eq(tokenJson), eq(RedisKey.REFRESH_TOKEN.getSecondDuration()));
    }


}
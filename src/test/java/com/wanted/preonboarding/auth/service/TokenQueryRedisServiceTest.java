package com.wanted.preonboarding.auth.service;

import com.wanted.preonboarding.auth.core.JwtAuthToken;
import com.wanted.preonboarding.data.auth.AuthModuleHelper;
import com.wanted.preonboarding.module.common.enums.RedisKey;
import com.wanted.preonboarding.module.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenQueryRedisServiceTest {

    @InjectMocks
    private TokenQueryRedisService tokenQueryRedisService;

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    @DisplayName("토큰 저장 테스트")
    void saveToken() {
        JwtAuthToken token = AuthModuleHelper.toJwtAuthToken();
        String tokenJson = JsonUtils.toJson(token);
        String key = tokenQueryRedisService.generateKey(RedisKey.REFRESH_TOKEN, token.getSubject());
        tokenQueryRedisService.saveToken(token);
        verify(valueOperations, times(1)).set(eq(key), eq(tokenJson), eq(RedisKey.REFRESH_TOKEN.getHourDuration()));
    }


}
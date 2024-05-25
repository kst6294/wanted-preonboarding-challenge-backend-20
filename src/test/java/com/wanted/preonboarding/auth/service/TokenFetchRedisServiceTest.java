package com.wanted.preonboarding.auth.service;

import com.wanted.preonboarding.auth.core.AuthToken;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenFetchRedisServiceTest {


    @InjectMocks
    private TokenFetchRedisService tokenFetchRedisService;

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }


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
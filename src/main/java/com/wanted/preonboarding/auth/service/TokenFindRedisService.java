package com.wanted.preonboarding.auth.service;

import com.wanted.preonboarding.auth.core.AuthToken;
import com.wanted.preonboarding.auth.core.JwtAuthToken;
import com.wanted.preonboarding.module.common.enums.RedisKey;
import com.wanted.preonboarding.module.common.service.AbstractRedisService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class TokenFindRedisService extends AbstractRedisService implements TokenFindService {

    public TokenFindRedisService(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public Optional<AuthToken> fetchToken(String email) {
        String key = RedisKey.REFRESH_TOKEN.generateKey(email);
        String value = getValue(key);
        if (StringUtils.hasText(value)) {
            return Optional.ofNullable(parseJson(value, JwtAuthToken.class));
        }

        return Optional.empty();
    }

}

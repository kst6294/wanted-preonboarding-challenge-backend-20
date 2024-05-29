package com.wanted.preonboarding.auth.service;

import com.wanted.preonboarding.auth.core.AuthToken;
import com.wanted.preonboarding.module.common.enums.RedisKey;
import com.wanted.preonboarding.module.common.service.AbstractRedisService;
import com.wanted.preonboarding.module.utils.JsonUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TokenQueryRedisService extends AbstractRedisService implements TokenQueryService{

    public TokenQueryRedisService(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public void saveToken(AuthToken token) {
        String key = generateKey(RedisKey.REFRESH_TOKEN, token.getSubject());
        String value = JsonUtils.toJson(token);
        save(key, value, RedisKey.REFRESH_TOKEN.getSecondDuration());
    }

}

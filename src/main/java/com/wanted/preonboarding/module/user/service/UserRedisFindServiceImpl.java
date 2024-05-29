package com.wanted.preonboarding.module.user.service;

import com.wanted.preonboarding.module.common.enums.RedisKey;
import com.wanted.preonboarding.module.common.service.AbstractRedisService;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.entity.Users;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserRedisFindServiceImpl extends AbstractRedisService implements UserRedisFindService{

    public UserRedisFindServiceImpl(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public Optional<BaseUserInfo> fetchUser(String email) {
        String key = generateKey(RedisKey.USERS, email);
        String value = getValue(key);
        if(StringUtils.hasText(value)) {
            return Optional.ofNullable(super.parseJson(value, BaseUserInfo.class));

        }
        return Optional.empty();
    }

    @Override
    public Optional<Users> fetchUserEntity(String email) {
        String key = generateKey(RedisKey.USERS_ENTITY, email);
        String value = getValue(key);
        if(StringUtils.hasText(value)) {
            return Optional.ofNullable(super.parseJson(value, Users.class));

        }
        return Optional.empty();
    }

}

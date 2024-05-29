package com.wanted.preonboarding.module.user.service;


import com.wanted.preonboarding.module.common.enums.RedisKey;
import com.wanted.preonboarding.module.common.service.AbstractRedisService;
import com.wanted.preonboarding.module.user.core.UserInfo;
import com.wanted.preonboarding.module.user.entity.Users;
import com.wanted.preonboarding.module.utils.JsonUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserRedisQueryServiceImpl extends AbstractRedisService implements UserRedisQueryService{

    public UserRedisQueryServiceImpl(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    public void saveInCache(UserInfo userInfo){
        String key = generateKey(RedisKey.USERS, userInfo.getEmail());
        String value = JsonUtils.toJson(userInfo);
        save(key, value, RedisKey.USERS.getSecondDuration());
    }

    @Override
    public void saveInCache(Users users) {
        String key = generateKey(RedisKey.USERS_ENTITY, users.getEmail());
        String value = JsonUtils.toJson(users);
        save(key, value, RedisKey.USERS_ENTITY.getSecondDuration());
    }

}

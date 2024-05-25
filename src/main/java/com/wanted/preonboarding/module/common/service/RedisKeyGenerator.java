package com.wanted.preonboarding.module.common.service;


import com.wanted.preonboarding.module.common.enums.RedisKey;

public interface RedisKeyGenerator {
    String generateKey(RedisKey redisKey, String key);

}

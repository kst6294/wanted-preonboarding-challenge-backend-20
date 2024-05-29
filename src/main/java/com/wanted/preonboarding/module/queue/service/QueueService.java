package com.wanted.preonboarding.module.common.service;

import com.wanted.preonboarding.module.common.enums.RedisKey;

import java.util.Optional;

public interface QueueService <T> {

    void push(RedisKey redisKey, T t);
    Optional<T> pop(String key);
}

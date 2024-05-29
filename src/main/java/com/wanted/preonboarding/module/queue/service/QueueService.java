package com.wanted.preonboarding.module.queue.service;

import com.wanted.preonboarding.module.common.enums.RedisKey;

import java.util.List;
import java.util.Optional;

public interface QueueService <T> {

    void push(RedisKey redisKey, T t);
    Optional<T> pop(RedisKey redisKey);
    List<String> getQueue(RedisKey redisKey);
}

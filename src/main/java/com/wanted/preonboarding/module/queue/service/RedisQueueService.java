package com.wanted.preonboarding.module.common.service;

import com.wanted.preonboarding.module.common.enums.RedisKey;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public abstract class RedisQueueService<T> implements QueueService<T>{

    private final StringRedisTemplate redisTemplate;


    @Override
    public void push(RedisKey redisKey, T t) {
        redisTemplate.opsForList().rightPush(redisKey.getKey(), String.valueOf(t));
    }

    @Override
    public Optional<T> pop(String key) {
        String value = redisTemplate.opsForList().leftPop(key);
        if (value != null) {
            return processItem(value);
        }
        return null;
    }

    protected abstract Optional<T> processItem(String value);


}

package com.wanted.preonboarding.module.queue.service;

import com.wanted.preonboarding.module.common.enums.RedisKey;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Optional<T> pop(RedisKey redisKey) {
        String value = redisTemplate.opsForList().leftPop(redisKey.getKey());
        if (value != null) {
            return processItem(value);
        }
        return Optional.empty();
    }

    @Override
    public List<String> getQueue(RedisKey redisKey) {
        return redisTemplate.opsForList().range(redisKey.getKey(), 0, -1);
    }

    protected abstract Optional<T> processItem(String value);


}

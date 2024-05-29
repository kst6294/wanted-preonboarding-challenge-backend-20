package com.wanted.preonboarding.module.queue.service;

import com.wanted.preonboarding.module.common.enums.RedisKey;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionQueueService extends RedisQueueService<String>{

    public SessionQueueService(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }


    public int getPosition(String sessionId) {
        List<String> queue = super.getQueue(RedisKey.PRODUCT_QUEUE);
        return queue.indexOf(sessionId) + 1;
    }


    @Override
    protected Optional<String> processItem(String value) {
        return Optional.of(value);
    }

}

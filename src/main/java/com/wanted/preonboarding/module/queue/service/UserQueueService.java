package com.wanted.preonboarding.module.queue.service;

import com.wanted.preonboarding.module.common.enums.RedisKey;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserQueueService extends RedisQueueService<String>{

    public UserQueueService(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    public int getPosition(String email) {
        List<String> queue = super.getQueue(RedisKey.PRODUCT_QUEUE);
        return queue.indexOf(email) + 1;
    }

    @Override
    protected Optional<String> processItem(String value) {
        return Optional.of(value);
    }

}

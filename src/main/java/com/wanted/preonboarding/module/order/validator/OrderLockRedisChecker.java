package com.wanted.preonboarding.module.order.validator;


import com.wanted.preonboarding.module.common.enums.RedisKey;
import com.wanted.preonboarding.module.common.service.AbstractRedisService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderLockRedisChecker extends AbstractRedisService implements OrderLockChecker {

    private static final String LOCK_SUCCESS = "LOCKED";

    public OrderLockRedisChecker(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    
    @Override
    public boolean lock(long productId) {
        String key = generateKey(RedisKey.ORDER_LOCK, String.valueOf(productId));
        if (!keyExists(key)) {
            save(key, LOCK_SUCCESS, RedisKey.ORDER_LOCK.getSecondDuration());
            return true;
        }
        return false;
    }


}

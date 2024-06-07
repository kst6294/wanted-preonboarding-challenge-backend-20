package com.wanted.market.stock.infra;

import com.wanted.market.order.exception.OutOfStockException;
import com.wanted.market.stock.service.StockManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class DistributedRedisStockManager implements StockManager {
    private final RedisTemplate<String, Integer> redisTemplate;

    public DistributedRedisStockManager(RedisTemplate<String, Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean isAvailable(Long productId) {
        Boolean result = redisTemplate.opsForValue().getOperations().hasKey(productId.toString());
        return Boolean.TRUE.equals(result);
    }

    @Override
    public Integer count(Long productId) {
        return redisTemplate.opsForValue().get(productId.toString());
    }

    @Override
    public Integer set(Long productId, Integer quantity) {
        redisTemplate.opsForValue().set(productId.toString(), quantity);
        return quantity;
    }

    @Override
    public Integer increment(Long productId) {
        return redisTemplate.opsForValue().increment(productId.toString()).intValue();
    }

    @Override
    public Integer increment(Long productId, Integer amount) {
        return redisTemplate.opsForValue().increment(productId.toString(), amount.longValue()).intValue();
    }

    @Override
    public Integer decrement(Long productId) {
        return redisTemplate.opsForValue().decrement(productId.toString()).intValue();
    }

    @Override
    public Integer decrement(Long productId, Integer amount) {
        return redisTemplate.opsForValue().decrement(productId.toString(), amount.longValue()).intValue();
    }

    @Override
    public Integer request(Long id) throws OutOfStockException {
        Integer leftStock = decrement(id);
        if (leftStock < 0)
            throw new OutOfStockException();
        return leftStock;
    }

    @Override
    public Integer register(Long productId, Integer quantity) {
        return set(productId, quantity);
    }
}

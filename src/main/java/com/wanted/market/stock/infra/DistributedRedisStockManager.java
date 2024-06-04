package com.wanted.market.stock.infra;

import com.wanted.market.order.exception.OutOfStockException;
import com.wanted.market.stock.service.StockManager;
import org.springframework.stereotype.Component;

// TODO: implement distributed redis stock manager
@Component
public class DistributedRedisStockManager implements StockManager {
    @Override
    public boolean isAvailable(Long productId) {
        return false;
    }

    @Override
    public Integer count(Long productId) {
        return 0;
    }

    @Override
    public Integer set(Long productId, Integer quantity) {
        return 0;
    }

    @Override
    public Integer increment(Long productId) {
        return 0;
    }

    @Override
    public Integer increment(Long productId, Integer amount) {
        return 0;
    }

    @Override
    public Integer decrement(Long productId) {
        return 0;
    }

    @Override
    public Integer decrement(Long productId, Integer amount) {
        return 0;
    }

    @Override
    public Integer request(Long id) throws OutOfStockException {
        return 0;
    }

    @Override
    public Integer register(Long productId, Integer quantity) {
        return 0;
    }
}

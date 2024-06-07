package com.wanted.market.stock.infra;

import com.wanted.market.order.exception.OutOfStockException;
import com.wanted.market.stock.service.StockManager;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

//@Component
public class InMemoryStockManager implements StockManager {
    private static ConcurrentMap<String, Integer> data = new ConcurrentHashMap<>();

    @Override
    public boolean isAvailable(Long productId) {
        return data.containsKey(productId.toString()) && data.get(productId.toString()) > 0;
    }

    @Override
    public Integer count(Long productId) {
        return data.get(productId.toString());
    }

    @Override
    public Integer set(Long productId, Integer quantity) {
        return data.put(productId.toString(), quantity);
    }

    @Override
    public Integer increment(Long productId) {
        return data.put(productId.toString(), data.get(productId.toString()) + 1);
    }

    @Override
    public Integer increment(Long productId, Integer amount) {
        return data.put(productId.toString(), data.get(productId.toString()) + amount);
    }

    @Override
    public Integer decrement(Long productId) {
        return data.put(productId.toString(), data.get(productId.toString()) - 1);
    }

    @Override
    public Integer decrement(Long productId, Integer amount) {
        return data.put(productId.toString(), data.get(productId.toString()) - amount);
    }

    @Override
    public Integer request(Long id) throws OutOfStockException {
        Integer currentStock = decrement(id);
        if (currentStock <= 0)
            throw new OutOfStockException();
        return currentStock - 1;
    }

    @Override
    public Integer register(Long productId, Integer quantity) {
        return set(productId, quantity);
    }
}

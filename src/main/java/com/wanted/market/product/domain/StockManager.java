package com.wanted.market.product.domain;

public interface StockManager {
    boolean isAvailable(Long productId);

    Integer count(Long productId);

    Integer register(Long productId, Integer quantity);

    Integer increment(Long productId);

    Integer increment(Long productId, Integer amount);

    Integer decrement(Long productId);

    Integer decrement(Long productId, Integer amount);
}

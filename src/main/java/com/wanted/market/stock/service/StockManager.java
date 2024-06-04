package com.wanted.market.stock.service;

import com.wanted.market.order.domain.StockRequester;
import com.wanted.market.product.domain.StockRegister;

public interface StockManager extends StockRequester, StockRegister {
    boolean isAvailable(Long productId);

    Integer count(Long productId);

    Integer set(Long productId, Integer quantity);

    Integer increment(Long productId);

    Integer increment(Long productId, Integer amount);

    Integer decrement(Long productId);

    Integer decrement(Long productId, Integer amount);
}

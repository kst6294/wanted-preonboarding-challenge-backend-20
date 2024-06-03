package com.wanted.market.product.domain;

public interface StockRegister {
    Integer register(Long productId, Integer quantity);
}

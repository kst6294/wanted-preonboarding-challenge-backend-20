package com.wanted.market.order.domain;

import com.wanted.market.order.exception.OutOfStockException;

public interface StockRequester {
    Integer request(Long id) throws OutOfStockException;
}

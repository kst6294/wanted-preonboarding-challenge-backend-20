package com.chaewon.wanted.domain.orders.service;

import com.chaewon.wanted.domain.orders.dto.OrderRequestDto;

public interface OrderService {
    void createOrder(String email, OrderRequestDto orderRequestDto);
}

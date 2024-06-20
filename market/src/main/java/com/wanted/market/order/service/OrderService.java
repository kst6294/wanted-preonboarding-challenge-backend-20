package com.wanted.market.order.service;

import com.wanted.market.order.dto.OrderRequestDto;
import com.wanted.market.order.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {
    OrderResponseDto order(String email, OrderRequestDto orderRequestDto);

    List<OrderResponseDto> getAll();

    OrderResponseDto approveOrder(String email, Integer orderId);
}

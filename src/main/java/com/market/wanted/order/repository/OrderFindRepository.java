package com.market.wanted.order.repository;

import com.market.wanted.order.dto.OrderDto;
import com.market.wanted.order.entity.Order;

import java.util.List;

public interface OrderFindRepository {
    List<OrderDto> findAllBySellerId(Long sellerId);
    List<OrderDto> findAllByBuyerId(Long buyerId);
}

package com.wanted.preonboarding.module.order.repository;

import com.wanted.preonboarding.module.order.entity.Order;

import java.util.Optional;

public interface OrderFindRepository {

    Optional<Order> fetchOrderEntity(long orderId);
}

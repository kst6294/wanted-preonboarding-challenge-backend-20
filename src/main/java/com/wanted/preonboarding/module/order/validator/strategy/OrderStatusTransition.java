package com.wanted.preonboarding.module.order.validator.strategy;

import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.enums.OrderStatus;

public interface OrderStatusTransition {
    OrderStatus getHandledStatus();
    boolean canTransition(OrderStatus toStatus, Order order, String currentUserEmail);
}

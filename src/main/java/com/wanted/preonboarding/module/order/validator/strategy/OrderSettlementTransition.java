package com.wanted.preonboarding.module.order.validator.strategy;


import com.wanted.preonboarding.module.exception.order.InvalidOrderUpdateIssueException;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class OrderSettlementTransition implements OrderStatusTransition {

    @Override
    public OrderStatus getHandledStatus() {
        return OrderStatus.COMPLETED;
    }

    @Override
    public boolean canTransition(OrderStatus toStatus, Order order, String currentUserEmail) {
        if(!order.getBuyer().getEmail().equals(currentUserEmail)) throw new InvalidOrderUpdateIssueException(order.getId(), order.getOrderStatus(), toStatus);
        return toStatus.isSettlement();
    }

}

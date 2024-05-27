package com.wanted.preonboarding.module.order.validator.strategy;

import com.wanted.preonboarding.module.common.provider.AbstractProvider;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderStatusTransitionProvider extends AbstractProvider<OrderStatus, OrderStatusTransition> {

    public OrderStatusTransitionProvider(List<OrderStatusTransition> services) {
        for (OrderStatusTransition service : services) {
            map.put(service.getHandledStatus(), service);
        }
    }
}

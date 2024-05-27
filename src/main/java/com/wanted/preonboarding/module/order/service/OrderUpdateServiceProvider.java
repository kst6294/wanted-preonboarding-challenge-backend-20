package com.wanted.preonboarding.module.order.service;

import com.wanted.preonboarding.module.common.provider.AbstractProvider;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderUpdateServiceProvider extends AbstractProvider<OrderStatus, OrderUpdateService> {

    public OrderUpdateServiceProvider(List<OrderUpdateService> services) {
        for (OrderUpdateService service : services) {
            map.put(service.getOrderStatus(), service);
        }
    }
}

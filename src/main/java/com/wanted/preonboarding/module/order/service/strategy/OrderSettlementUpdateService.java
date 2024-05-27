package com.wanted.preonboarding.module.order.service.strategy;

import com.wanted.preonboarding.module.order.enums.OrderStatus;
import com.wanted.preonboarding.module.order.mapper.OrderMapper;
import com.wanted.preonboarding.module.order.service.OrderFindService;
import org.springframework.stereotype.Service;

@Service
public class OrderSettlementUpdateService extends AbstractOrderUpdateService{

    public OrderSettlementUpdateService(OrderFindService orderFindService, OrderMapper orderMapper) {
        super(orderFindService, orderMapper);
    }

    @Override
    public OrderStatus getOrderStatus() {
        return OrderStatus.COMPLETED;
    }
}

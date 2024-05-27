package com.wanted.preonboarding.module.order.service.strategy;

import com.wanted.preonboarding.module.order.enums.OrderStatus;
import com.wanted.preonboarding.module.order.mapper.OrderMapper;
import com.wanted.preonboarding.module.order.service.OrderFindService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class OrderCompletedUpdateService extends AbstractOrderUpdateService {

    public OrderCompletedUpdateService(OrderFindService orderFindService, OrderMapper orderMapper) {
        super(orderFindService, orderMapper);
    }

    @Override
    public OrderStatus getOrderStatus() {
        return OrderStatus.ORDERED;
    }
}

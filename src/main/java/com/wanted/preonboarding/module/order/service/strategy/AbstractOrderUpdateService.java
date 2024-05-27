package com.wanted.preonboarding.module.order.service.strategy;


import com.wanted.preonboarding.module.order.core.OrderContext;
import com.wanted.preonboarding.module.order.dto.UpdateOrder;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.mapper.OrderMapper;
import com.wanted.preonboarding.module.order.service.OrderFindService;
import com.wanted.preonboarding.module.order.service.OrderUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public abstract class AbstractOrderUpdateService implements OrderUpdateService {

    private final OrderFindService orderFindService;
    private final OrderMapper orderMapper;


    @Override
    public OrderContext updateOrder(UpdateOrder updateOrder) {
        Order order = orderFindService.fetchOrderEntity(updateOrder.getOrderId());
        order.changeOrderStatus(updateOrder.getOrderStatus());
        return orderMapper.toOrderContext(order);
    }

}

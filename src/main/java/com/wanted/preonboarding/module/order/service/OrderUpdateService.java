package com.wanted.preonboarding.module.order.service;

import com.wanted.preonboarding.module.order.core.OrderContext;
import com.wanted.preonboarding.module.order.dto.UpdateOrder;
import com.wanted.preonboarding.module.order.enums.OrderStatus;

public interface OrderUpdateService {

    OrderStatus getOrderStatus();
    OrderContext updateOrder(UpdateOrder updateOrder);


}

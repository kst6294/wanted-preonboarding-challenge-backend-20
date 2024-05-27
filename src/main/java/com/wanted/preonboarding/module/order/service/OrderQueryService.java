package com.wanted.preonboarding.module.order.service;

import com.wanted.preonboarding.module.order.core.OrderContext;
import com.wanted.preonboarding.module.order.dto.CreateOrder;

public interface OrderQueryService {

    OrderContext doOrder(CreateOrder createOrder);

}

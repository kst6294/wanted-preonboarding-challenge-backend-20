package com.wanted.preonboarding.module.order.service;

import com.wanted.preonboarding.module.order.entity.Order;

public interface OrderFindService {

    Order fetchOrderEntity(long orderId);

}

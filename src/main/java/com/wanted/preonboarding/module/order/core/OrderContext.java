package com.wanted.preonboarding.module.order.core;

import com.wanted.preonboarding.module.common.mapper.LastDomainIdProvider;
import com.wanted.preonboarding.module.order.enums.OrderStatus;

public interface OrderContext extends LastDomainIdProvider {
    long getOrderId();
    long getProductId();
    OrderStatus getOrderStatus();
    String getBuyer();
    String getSeller();


}

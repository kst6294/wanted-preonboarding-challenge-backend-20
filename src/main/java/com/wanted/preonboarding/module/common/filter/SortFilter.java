package com.wanted.preonboarding.module.common.filter;

import com.wanted.preonboarding.module.common.enums.OrderType;

public interface SortFilter {

    OrderType getOrderType();
    void setOrderType(OrderType orderType);
}

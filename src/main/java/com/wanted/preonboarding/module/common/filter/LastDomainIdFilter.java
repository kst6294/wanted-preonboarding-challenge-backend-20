package com.wanted.preonboarding.module.common.filter;

import com.wanted.preonboarding.module.common.enums.OrderType;

public interface LastDomainIdFilter {
    Long getLastDomainId();
    void setLastDomainId(Long lastDomainId);
    String getCursorValue();
    void setCursorValue(String cursorValue);
    OrderType getOrderType();
    void setOrderType(OrderType orderType);

}

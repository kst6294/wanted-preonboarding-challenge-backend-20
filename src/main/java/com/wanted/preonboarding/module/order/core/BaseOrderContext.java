package com.wanted.preonboarding.module.order.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class BaseOrderContext implements OrderContext{

    private long orderId;
    private long productId;
    private String buyer;
    private String seller;
    private OrderStatus orderStatus;

    @JsonIgnore
    @Override
    public Long getId() {
        return orderId;
    }


}

package com.wanted.market.order.exception;

import lombok.Getter;

@Getter
public class OrderException extends RuntimeException {

    private final OrderErrorCode orderErrorCode;

    public OrderException(OrderErrorCode orderErrorCode) {
        super(orderErrorCode.getMessage());
        this.orderErrorCode = orderErrorCode;
    }
}

package com.wanted.market.order.exception;


public class OrderException extends RuntimeException {

    private OrderErrorCode orderErrorCode;

    public OrderException(OrderErrorCode orderErrorCode) {
    }
}

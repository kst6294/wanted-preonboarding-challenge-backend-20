package com.wanted.market.order.event;

public record OrderConfirmedEvent(
        Long orderId,
        Long productId,
        Integer leftStock
) {
}

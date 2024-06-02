package com.wanted.market.order.event;

public record OrderConfirmedEvent(
        Long id,
        Long buyerId,
        Long productId
) {
}

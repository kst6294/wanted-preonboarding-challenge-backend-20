package com.wanted.market.order.event;

public record OrderFinishedEvent(
        Long orderId,
        Long productId
) {
}

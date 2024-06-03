package com.wanted.market.order.event;

public record OrderFinishedEvent(
        Long id,
        Long buyerId,
        Long productId
) {
}

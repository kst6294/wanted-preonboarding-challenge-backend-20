package com.wanted.market.order.event;

import com.wanted.market.common.event.Event;

public record OrderConfirmedEvent(
        Long orderId,
        Long productId,
        Integer leftStock
) implements Event {
}

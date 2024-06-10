package com.wanted.market.order.event;

import com.wanted.market.common.event.Event;

public record OrderFinishedEvent(
        Long orderId,
        Long productId
) implements Event {
}

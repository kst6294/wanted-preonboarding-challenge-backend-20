package com.wanted.market.global.event;

import com.wanted.market.common.event.Event;
import org.springframework.context.ApplicationEventPublisher;

public class Events {
    private static ApplicationEventPublisher eventPublisher;

    public static void publish(Event event) {
        eventPublisher.publishEvent(event);
    }

    public static void setPublisher(ApplicationEventPublisher publisher) {
        eventPublisher = publisher;
    }
}

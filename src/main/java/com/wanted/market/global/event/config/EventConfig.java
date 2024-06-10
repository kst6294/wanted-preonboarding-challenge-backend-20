package com.wanted.market.global.event.config;

import com.wanted.market.global.event.Events;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {
    @Bean
    public InitializingBean eventsInitializer(ApplicationEventPublisher applicationEventPublisher) {
        return () -> Events.setPublisher(applicationEventPublisher);
    }
}

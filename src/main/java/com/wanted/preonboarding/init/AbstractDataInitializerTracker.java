package com.wanted.preonboarding.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public abstract class AbstractDataInitializerTracker implements DataInitializerTracker {


    @Override
    public void start(String entity) {
        log.info("Starting data initializer for {}", entity);
    }

    @Override
    public void end(String entity) {
        log.info("Finished data initializer for {}", entity);
    }

}

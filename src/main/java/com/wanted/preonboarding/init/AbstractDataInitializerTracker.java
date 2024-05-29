package com.wanted.preonboarding;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public abstract class AbstractDataInitializer implements DataInitializerInterface{


    @Override
    public void initialize() {

    }


    private void buildMessage(String entity){
        
    }
}

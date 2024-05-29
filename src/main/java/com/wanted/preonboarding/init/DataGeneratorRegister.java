package com.wanted.preonboarding.init;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataGeneratorRegister {

    private final UserDataInitializer userDataInitializer;
    private final ProductDataInitializer productDataInitializer;
    private final OrderDataInitializer orderDataInitializer;

    @PostConstruct
    public void registerGenerators() {
        DataProvider.addGenerator(userDataInitializer, 20);
        DataProvider.addGenerator(productDataInitializer, 100);
        DataProvider.addGenerator(orderDataInitializer, 30);
    }

}

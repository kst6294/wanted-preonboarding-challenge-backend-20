package com.wanted.preonboarding.data;

import org.jeasy.random.api.Randomizer;

import java.util.Random;

public class FourDigitRandomizer implements Randomizer<Integer> {

    private final Random random;

    public FourDigitRandomizer() {
        this.random = new Random();
    }

    @Override
    public Integer getRandomValue() {
        return 1000 + random.nextInt(9000); // 1000부터 9999 사이의 숫자 생성
    }
}
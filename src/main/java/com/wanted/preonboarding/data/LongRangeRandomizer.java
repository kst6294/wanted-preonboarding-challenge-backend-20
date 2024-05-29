package com.wanted.preonboarding.data;

import org.jeasy.random.api.Randomizer;

class LongRangeRandomizer implements Randomizer<Long> {
    private final long min;
    private final long max;

    public LongRangeRandomizer(long min, long max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Long getRandomValue() {
        return min + (long) (Math.random() * (max - min));
    }
}


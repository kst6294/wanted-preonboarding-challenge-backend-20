package com.wanted.preonboarding.data;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.util.Map;

import static org.jeasy.random.FieldPredicates.named;


public class EasyRandomUtils {
    private static final EasyRandom easyRandom;

    static {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .excludeField(named("id"))
                .randomize(Long.class, new LongRangeRandomizer(1, 100000000))
                .randomize(String.class, new StringRandomizer(10));
        easyRandom = new EasyRandom(parameters);
    }

    public static EasyRandom getInstance() {
        return easyRandom;
    }

    public static EasyRandom getInstance(Map<String, Object> values) {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .excludeField(named("id"))
                .randomize(Long.class, new LongRangeRandomizer(1, 100000000))
                .randomize(String.class, new StringRandomizer(10));

        for(Map.Entry<String, Object> entry : values.entrySet()){
            parameters.randomize(field -> field.getName().equals(entry.getKey()), entry::getValue);
        }

        return new EasyRandom(parameters);
    }


}

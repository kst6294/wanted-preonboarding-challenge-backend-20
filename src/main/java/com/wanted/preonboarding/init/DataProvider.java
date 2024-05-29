package com.wanted.preonboarding.init;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class DataProvider {

    @Getter
    private static final Set<DataGenerator> generators = new LinkedHashSet<>();
    private static final Map<DataGenerator, Integer> generatorSizes = new HashMap<>();

    public static void addGenerator(DataGenerator generator, int size) {
        generators.add(generator);
        generatorSizes.put(generator, size);
    }

    public static int getSize(DataGenerator generator) {
        return generatorSizes.getOrDefault(generator, 10);
    }

}

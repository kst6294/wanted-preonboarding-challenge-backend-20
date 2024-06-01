package com.wanted.challenge.product.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PriceConverter implements AttributeConverter<Price, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Price attribute) {
        return attribute.value();
    }

    @Override
    public Price convertToEntityAttribute(Integer dbData) {
        return new Price(dbData);
    }
}

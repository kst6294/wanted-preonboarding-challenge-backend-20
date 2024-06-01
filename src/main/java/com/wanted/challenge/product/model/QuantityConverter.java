package com.wanted.challenge.product.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class QuantityConverter implements AttributeConverter<Quantity, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Quantity attribute) {
        return attribute.value();
    }

    @Override
    public Quantity convertToEntityAttribute(Integer dbData) {
        return new Quantity(dbData);
    }
}

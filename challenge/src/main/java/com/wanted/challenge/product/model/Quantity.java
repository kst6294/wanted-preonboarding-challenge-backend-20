package com.wanted.challenge.product.model;

import com.wanted.challenge.exception.CustomException;
import com.wanted.challenge.exception.ExceptionStatus;

public record Quantity(int value) {
    public Quantity(int value) {
        validValue(value);
        this.value = value;
    }

    public static Quantity minus(Quantity quantity) {
        return new Quantity(quantity.value - 1);
    }

    private void validValue(int value) {
        if (value < 0) {
            throw new CustomException(ExceptionStatus.NEGATIVE_QUANTITY);
        }
    }
}

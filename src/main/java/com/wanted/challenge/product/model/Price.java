package com.wanted.challenge.product.model;

import com.wanted.challenge.exception.CustomException;
import com.wanted.challenge.exception.ExceptionStatus;

public record Price(int value) {

    public Price {
        validValue(value);
    }

    private void validValue(int value) {
        if (value <= 0) {
            throw new CustomException(ExceptionStatus.NEGATIVE_PRICE);
        }
    }
}

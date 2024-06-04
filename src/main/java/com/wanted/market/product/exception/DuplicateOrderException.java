package com.wanted.market.product.exception;

import com.wanted.market.common.exception.InvalidRequestException;

public class DuplicateOrderException extends InvalidRequestException {
    public DuplicateOrderException(String message) {
        super(message);
    }

    public DuplicateOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}

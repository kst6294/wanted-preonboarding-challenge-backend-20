package com.challenge.market.product.exception;

public class ProductRegistrationFailedException extends RuntimeException {

    public ProductRegistrationFailedException() {
        super();
    }

    /**
     * 메시지를 넘긴다.
     *
     * @param message
     */
    public ProductRegistrationFailedException(String message) {
        super(message);
    }

    /**
     * message와 cause를 넘긴다.
     *
     * @param message
     * @param cause
     */
    public ProductRegistrationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}

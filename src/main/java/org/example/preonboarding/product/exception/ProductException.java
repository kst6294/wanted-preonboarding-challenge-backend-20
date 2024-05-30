package org.example.preonboarding.product.exception;

public class ProductException extends Exception {

    public ProductException() {
        super();
    }

    public ProductException(String message) {
        super(message);
    }

    public ProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductException(Throwable cause) {
        super(cause);
    }
}

package org.example.preonboarding.order.exception;

public class NotFoundOrderException extends RuntimeException {
    public NotFoundOrderException() {
        super();
    }

    public NotFoundOrderException(String message) {
        super(message);
    }

    public NotFoundOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundOrderException(Throwable cause) {
        super(cause);
    }
}

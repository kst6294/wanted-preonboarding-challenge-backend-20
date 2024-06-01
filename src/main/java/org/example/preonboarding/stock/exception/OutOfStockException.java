package org.example.preonboarding.stock.exception;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException() {
        super();
    }

    public OutOfStockException(String message) {
        super(message);
    }

    public OutOfStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfStockException(Throwable cause) {
        super(cause);
    }
}

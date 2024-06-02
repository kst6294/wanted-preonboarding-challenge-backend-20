package com.chaewon.wanted.domain.orders.exception;

public class DuplicateApprovalException extends RuntimeException {
    public DuplicateApprovalException (String message) {
        super(message);
    }
}

package com.wanted.preonboarding.domain.product.entity;

public enum ProductState {
    SALE, RESERVATION, COMPLETE;

    public String toString() {
        return this.name();
    }
}

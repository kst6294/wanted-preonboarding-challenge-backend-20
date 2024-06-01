package com.wanted.preonboarding.domain.purchase.entity;

public enum PurchaseState {
    PENDING, ACCEPT_SALE, CONFIRM_PURCHASE;

    public String toString() {
        return this.name();
    }
}

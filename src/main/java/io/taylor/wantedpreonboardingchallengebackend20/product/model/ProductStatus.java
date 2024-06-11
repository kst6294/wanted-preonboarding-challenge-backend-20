package io.taylor.wantedpreonboardingchallengebackend20.product.model;

public enum ProductStatus {
    Available(0),
    Reserved(1),
    Completed(2);

    private final int value;

    ProductStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

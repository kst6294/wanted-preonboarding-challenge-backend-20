package com.wanted.market.product.domain;

public interface OnGoingReservationChecker {
    boolean exists(Long productId);
    Long count(Long productId);
}

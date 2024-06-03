package com.wanted.market.order.domain;

public interface SellerChecker {
    boolean check(Long productId, Long sellerId);
}

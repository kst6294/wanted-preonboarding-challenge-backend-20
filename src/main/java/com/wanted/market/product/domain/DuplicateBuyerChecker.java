package com.wanted.market.product.domain;

public interface DuplicateBuyerChecker {
    boolean check(Long buyerId, Long productId);
}

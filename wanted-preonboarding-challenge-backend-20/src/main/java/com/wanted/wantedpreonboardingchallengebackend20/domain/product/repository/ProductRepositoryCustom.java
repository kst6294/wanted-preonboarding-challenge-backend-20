package com.wanted.wantedpreonboardingchallengebackend20.domain.product.repository;

public interface ProductRepositoryCustom {
    void modifyProductState(Long productId);
    void sellProduct(Long productId);
}

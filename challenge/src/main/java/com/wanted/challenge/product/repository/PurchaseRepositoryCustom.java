package com.wanted.challenge.product.repository;

import com.wanted.challenge.product.model.PurchaseDetail;

public interface PurchaseRepositoryCustom {

    PurchaseDetail retrieveLastPurchaseDetail(Long buyerId, Long productId);

    boolean isPurchaseAlready(Long buyerId, Long productId);
}

package com.wanted.challenge.purchase.repository;

import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.purchase.model.PurchaseDetail;
import java.util.Set;

public interface PurchaseRepositoryCustom {

    PurchaseDetail retrieveLastPurchaseDetail(Long buyerId, Long productId);

    boolean isPurchaseAlready(Long buyerId, Long productId);

    Set<PurchaseDetail> retrieveProductPurchaseDetails(Product product);
}

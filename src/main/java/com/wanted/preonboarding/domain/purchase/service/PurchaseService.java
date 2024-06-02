package com.wanted.preonboarding.domain.purchase.service;

import com.wanted.preonboarding.domain.purchase.dto.request.PurchaseRequest;

public interface PurchaseService {
    void purchase(Long userId, PurchaseRequest purchaseRequest);
    void accept(Long userId, PurchaseRequest purchaseRequest);
}

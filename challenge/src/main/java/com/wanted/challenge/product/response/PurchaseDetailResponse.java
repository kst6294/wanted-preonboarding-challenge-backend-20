package com.wanted.challenge.product.response;

import com.wanted.challenge.purchase.model.PurchaseDetail;

public record PurchaseDetailResponse(PurchaseDetail purchaseDetail) implements PurchaseInfo {
}

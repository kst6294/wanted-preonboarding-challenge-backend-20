package com.wanted.challenge.product.response;

import com.wanted.challenge.product.model.PurchaseDetail;

public record PurchaseDetailResponse(PurchaseDetail purchaseDetail) implements PurchaseInfo {
}

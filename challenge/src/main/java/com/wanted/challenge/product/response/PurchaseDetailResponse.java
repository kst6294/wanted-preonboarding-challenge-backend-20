package com.wanted.challenge.product.response;

import com.wanted.challenge.transact.model.TransactDetail;

public record PurchaseDetailResponse(TransactDetail transactDetail) implements PurchaseInfo {
}

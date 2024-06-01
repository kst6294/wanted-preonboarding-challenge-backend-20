package com.wanted.challenge.product.response;

import com.wanted.challenge.transact.model.TransactState;

public record PurchaseDetailResponse(TransactState transactState) implements PurchaseInfo {
}

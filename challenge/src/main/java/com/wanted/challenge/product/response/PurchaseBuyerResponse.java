package com.wanted.challenge.product.response;

import com.wanted.challenge.transact.model.TransactState;
import java.util.List;

public record PurchaseBuyerResponse(Long buyerId, List<TransactState> transactState) implements PurchaseInfo {
}

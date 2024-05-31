package com.wanted.challenge.product.response;

import com.wanted.challenge.transact.model.TransactDetail;
import java.util.List;

public record PurchaseBuyerResponse(Long buyerId, List<TransactDetail> transactDetail) implements PurchaseInfo {
}

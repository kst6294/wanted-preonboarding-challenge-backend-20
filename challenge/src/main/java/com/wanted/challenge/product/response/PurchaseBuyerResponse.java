package com.wanted.challenge.product.response;

import com.wanted.challenge.product.model.PurchaseDetail;
import java.util.List;

public record PurchaseBuyerResponse(Long buyerId, List<PurchaseDetail> purchaseDetail) implements PurchaseInfo {
}

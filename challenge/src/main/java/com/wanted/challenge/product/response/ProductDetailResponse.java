package com.wanted.challenge.product.response;

import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.product.model.PurchaseDetail;
import com.wanted.challenge.product.model.Reservation;
import java.util.List;

public record ProductDetailResponse(String productName, int price, Reservation reservation,
                                    List<PurchaseDetail> purchaseDetails) {

    public ProductDetailResponse(Product product, List<PurchaseDetail> purchaseDetails) {
        this(product.getName(), product.getPrice().value(), product.getReservation(), purchaseDetails);
    }
}

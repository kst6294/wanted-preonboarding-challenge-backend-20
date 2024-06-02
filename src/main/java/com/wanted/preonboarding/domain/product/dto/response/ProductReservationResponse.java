package com.wanted.preonboarding.domain.product.dto.response;

import com.wanted.preonboarding.domain.product.entity.Product;
import com.wanted.preonboarding.domain.purchase.dto.response.PurchaseProductResponse;
import com.wanted.preonboarding.domain.purchase.entity.Purchase;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductReservationResponse {
    List<ProductResponse> sell;
    List<PurchaseProductResponse> bought;

    public static ProductReservationResponse of(List<Product>productList, List<Purchase> purchaseList) {
        List<ProductResponse> sellList = new ArrayList<>();
        for(Product product : productList) sellList.add(ProductResponse.of(product));
        List<PurchaseProductResponse> boughtList = new ArrayList<>();
        for(Purchase purchase : purchaseList) boughtList.add(PurchaseProductResponse.of(purchase));
        return ProductReservationResponse.builder()
                .sell(sellList)
                .bought(boughtList)
                .build();
    }
}

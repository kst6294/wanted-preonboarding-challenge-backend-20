package com.wanted.preonboarding.domain.product.dto.response;

import com.wanted.preonboarding.domain.product.entity.Product;
import com.wanted.preonboarding.domain.purchase.dto.response.PurchaseResponse;
import com.wanted.preonboarding.domain.purchase.entity.Purchase;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductDetailResponse<T> {
    private ProductResponse product;
    private T purchase; // 구매자의 경우 PurchaseResponse, 판매자의 경우 List<PurchaseResponse>


    public static ProductDetailResponse of(Product product) {
        return ProductDetailResponse.builder()
                .product(ProductResponse.of(product))
                .build();
    }

    public static ProductDetailResponse of(Product product, Purchase purchase) {
        return ProductDetailResponse.builder()
                .product(ProductResponse.of(product))
                .purchase(purchase != null ? PurchaseResponse.of(purchase) : null)
                .build();
    }

    public static ProductDetailResponse of(Product product, List<Purchase> purchaseList) {
        List<PurchaseResponse> purchaseResponseList = new ArrayList<>();
        for(Purchase purchase : purchaseList) purchaseResponseList.add(PurchaseResponse.of(purchase));
        return ProductDetailResponse.builder()
                .product(ProductResponse.of(product))
                .purchase(purchaseResponseList)
                .build();
    }
}

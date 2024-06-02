package com.wanted.preonboarding.domain.purchase.dto.response;

import com.wanted.preonboarding.domain.product.dto.response.ProductCustomerResponse;
import com.wanted.preonboarding.domain.product.dto.response.ProductResponse;
import com.wanted.preonboarding.domain.product.entity.Product;
import com.wanted.preonboarding.domain.purchase.entity.Purchase;
import com.wanted.preonboarding.domain.purchase.entity.PurchaseState;
import com.wanted.preonboarding.domain.user.dto.response.UserResponse;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PurchaseProductResponse {
    private Long id; // 구매 아이디
    private ProductCustomerResponse product;
    private PurchaseState state; // 주문 상태

    public static PurchaseProductResponse of(Purchase purchase) {
        return PurchaseProductResponse.builder()
                .id(purchase.getId())
                .product(ProductCustomerResponse.of(purchase.getProduct(), purchase.getPrice()))
                .state(purchase.getState())
                .build();
    }

}

package com.wanted.wantedpreonboardingchallengebackend20.domain.user.dto.response;

import com.wanted.wantedpreonboardingchallengebackend20.domain.product.entity.Product;
import com.wanted.wantedpreonboardingchallengebackend20.domain.product.entity.ProductState;
import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SellingProductListResponseDto {
    private List<ProductResponseDto> productList;
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ProductResponseDto{
        private String productName;
        private ProductState state;
        private int price;
        private int quantity;
    }
}

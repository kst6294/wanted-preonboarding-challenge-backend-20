package com.wanted.preonboarding.domain.product.dto.response;

import com.wanted.preonboarding.domain.product.entity.Product;
import com.wanted.preonboarding.domain.product.entity.ProductState;
import com.wanted.preonboarding.domain.purchase.entity.PurchaseState;
import com.wanted.preonboarding.domain.user.dto.response.UserResponse;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductResponse {
    private Long id; // 아이디
    private UserResponse seller; // 판매자
    private String name; // 제품명
    private Integer price; // 가격
    private Integer quantity; // 재고
    private ProductState state; // 상태

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .seller(UserResponse.of(product.getUser()))
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .state(product.getState())
                .build();
    }

}

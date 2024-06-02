package com.wanted.preonboarding.domain.product.dto.response;


import com.wanted.preonboarding.domain.product.entity.Product;
import com.wanted.preonboarding.domain.user.dto.response.UserResponse;
import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ProductCustomerResponse {
    private Long id; // 상품 아이디
    private String name; // 상품명
    private Integer price; // 가격
    private UserResponse seller; // 판매자

    public static ProductCustomerResponse of(Product product) {
        return ProductCustomerResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .seller(UserResponse.of(product.getUser()))
                .build();
    }

    public static ProductCustomerResponse of(Product product, Integer price) {
        return ProductCustomerResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(price)
                .seller(UserResponse.of(product.getUser()))
                .build();
    }
}
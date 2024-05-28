package com.wanted.preonboarding.backend20.domain.product.dto;

import com.wanted.preonboarding.backend20.domain.product.domain.Product;
import com.wanted.preonboarding.backend20.domain.product.enums.ProductStatus;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProductOutlineDto {
    private Long id;
    private String name;
    private int price;
    private ProductStatus status;

    public static ProductOutlineDto toProductOutlineDto(Product product) {
        return ProductOutlineDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .status(product.getStatus())
                .build();
    }
}
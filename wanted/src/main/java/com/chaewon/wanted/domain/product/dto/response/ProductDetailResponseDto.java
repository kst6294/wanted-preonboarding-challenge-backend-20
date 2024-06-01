package com.chaewon.wanted.domain.product.dto.response;

import com.chaewon.wanted.domain.product.entity.Product;
import com.chaewon.wanted.domain.product.entity.ProductStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetailResponseDto {
    private Long id;
    private String name;
    private int price;
    private ProductStatus productStatus;

    public static ProductDetailResponseDto from(Product product) {
        return ProductDetailResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .productStatus(product.getProductStatus())
                .build();
    }
}

package com.wanted.market.product.dto;

import com.wanted.market.product.domain.Product;
import com.wanted.market.product.model.ProductStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

    private Integer id;

    private String name;

    private Integer price;

    private ProductStatus status;

    public static ProductResponseDto createFromEntity(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .status(ProductStatus.ON_SALE)
                .build();
    }
}

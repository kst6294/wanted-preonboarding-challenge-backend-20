package com.wanted.market.product.dto;

import com.wanted.market.product.domain.Product;
import com.wanted.market.product.model.ProductStatus;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    private Integer id;

    private String name;

    private Integer price;

    private ProductStatus status;


    public Product toEntity() {
        return Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .status(ProductStatus.ON_SALE)
                .build();
    }

}

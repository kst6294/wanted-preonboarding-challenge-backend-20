package com.wanted.market.product.dto;

import com.wanted.market.member.domain.Member;
import com.wanted.market.product.domain.Product;
import com.wanted.market.product.model.ProductStatus;
import lombok.*;


@Getter
@Builder
public class ProductRequestDto {

    private Integer id;

    private String name;

    private Integer price;

    private Integer quantity;


    public Product toEntity() {
        return Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .quantity(quantity)
                .status(ProductStatus.ON_SALE)
                .build();
    }

}

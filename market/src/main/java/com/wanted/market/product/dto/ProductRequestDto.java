package com.wanted.market.product.dto;

import com.wanted.market.member.domain.Member;
import com.wanted.market.product.domain.Product;
import com.wanted.market.product.model.ProductStatus;
import lombok.*;


@Getter
@Builder
@Setter
public class ProductRequestDto {

    private String productName;
    private Integer price;
    private Integer quantity;
    private Member seller;

    public Product toEntity(Member member) {
        return Product.builder()
                .name(productName)
                .price(price)
                .quantity(quantity)
                .productStatus(ProductStatus.ON_SALE)
                .seller(member)
                .build();
    }

}

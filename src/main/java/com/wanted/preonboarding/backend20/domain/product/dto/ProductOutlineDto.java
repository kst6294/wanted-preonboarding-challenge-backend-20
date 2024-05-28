package com.wanted.preonboarding.backend20.domain.product.dto;

import com.wanted.preonboarding.backend20.domain.product.domain.Product;
import com.wanted.preonboarding.backend20.domain.product.enums.ProductStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOutlineDto {
    private Long id;
    private String name;
    private int price;
    private ProductStatus status;

    @Builder
    public ProductOutlineDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.status = product.getStatus();
    }
}
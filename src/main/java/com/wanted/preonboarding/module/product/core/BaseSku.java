package com.wanted.preonboarding.module.product.core;

import com.querydsl.core.annotations.QueryProjection;
import com.wanted.preonboarding.module.product.enums.ProductStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class BaseSku implements Sku {

    private Long id;
    private String productName;
    private long price;
    private ProductStatus productStatus;
    private String seller;
    private int quantity;

    @QueryProjection
    public BaseSku(Long id, String productName, long price, ProductStatus productStatus, String seller, int quantity) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.productStatus = productStatus;
        this.seller = seller;
        this.quantity = quantity;
    }
}

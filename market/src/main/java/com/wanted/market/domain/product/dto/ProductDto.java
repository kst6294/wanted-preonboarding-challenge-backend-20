package com.wanted.market.domain.product.dto;

import com.wanted.market.domain.product.entity.ProductStatusCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private long productNo;

    private String name;

    private long price;

    private ProductStatusCode status;

    public ProductDto() {
    }

    public ProductDto(long productNo, String name, long price, ProductStatusCode status) {
        this.productNo = productNo;
        this.name = name;
        this.price = price;
        this.status = status;
    }
}

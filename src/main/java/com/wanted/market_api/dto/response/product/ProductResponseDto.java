package com.wanted.market_api.dto.response.product;

import com.wanted.market_api.constant.ProductStatus;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ProductResponseDto {
    private long id;
    private String name;
    private int price;
    private int count;
    private ProductStatus productStatus;
}

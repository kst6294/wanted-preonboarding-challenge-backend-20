package com.wanted.market_api.dto.request.product;

import lombok.Getter;

@Getter
public class ProductCreateRequestDto {
    private String name;
    private int price;
    private int count;
}

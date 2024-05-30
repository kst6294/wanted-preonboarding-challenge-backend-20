package com.market.wanted.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddProduct {

    @NotNull(message = "상품명이 비어있습니다.")
    private String productName;

    @NotNull(message = "상품 가격이 비어있습니다.")
    @Min(value = 0, message = "상품 가격은 0이상입니다.")
    private long price;
}

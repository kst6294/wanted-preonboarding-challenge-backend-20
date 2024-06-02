package com.example.wanted.product.domain;

import com.example.wanted.user.infrastucture.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCreate {
    @NotNull(message = "제품 명이 비어있습니다")
    @Schema(description = "제품 명", nullable = true, example = "돼지바")
    private String name;
    @NotNull(message = "가격이 비어있습니다.")
    @Schema(description = "가격", nullable = true, example = "1000")
    private int price;
    @NotNull(message = "재고가 입력되지 않았습니다.")
    @Schema(description = "재고", nullable = false, example = "10")
    private int quantity;

    @Builder
    public ProductCreate(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }


}

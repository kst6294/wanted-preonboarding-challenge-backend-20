package com.example.wantedmarketapi.dto.request;

import lombok.Getter;

public class ProductRequestDto {

    @Getter
    public static class CreateProductRequest {
        String name;
        Integer price;
    }

}

package com.example.wantedmarketapi.dto.response;

import lombok.*;

public class ProductResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateProductResponse {
        Long id;
        String name;
    }

}

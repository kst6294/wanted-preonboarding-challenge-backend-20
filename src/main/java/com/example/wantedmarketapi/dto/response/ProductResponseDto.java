package com.example.wantedmarketapi.dto.response;

import com.example.wantedmarketapi.domain.enums.ProductStatus;
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

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetProductResponse {
        String name;
        Integer price;
        ProductStatus productStatus;
        Boolean reservationStatus;
    }

}

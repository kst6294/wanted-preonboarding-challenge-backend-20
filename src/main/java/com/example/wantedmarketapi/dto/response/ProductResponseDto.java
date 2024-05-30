package com.example.wantedmarketapi.dto.response;

import com.example.wantedmarketapi.domain.Trade;
import com.example.wantedmarketapi.domain.enums.ProductStatus;
import lombok.*;

import java.util.List;

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


    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetProductDetailsResponse {
        String name;
        Integer price;
        ProductStatus productStatus;
        Boolean reservationStatus;
        List<Trade> tradeList;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SetProductStatusResponse {
        String name;
        ProductStatus productStatus;
    }

}

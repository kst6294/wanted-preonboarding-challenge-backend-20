package org.example.wantedmarket.dto.product;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.example.wantedmarket.model.Product;

public class ProductUpdateDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @NotNull
        private Long productId;
        @NotNull
        private Integer price;

    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long productId;
        private String name;
        private Integer price;

        public static ProductUpdateDto.Response from(Product product) {
            return ProductUpdateDto.Response.builder()
                    .productId(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .build();
        }

    }

}

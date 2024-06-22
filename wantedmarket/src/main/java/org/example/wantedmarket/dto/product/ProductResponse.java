package org.example.wantedmarket.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.wantedmarket.dto.user.UserResponse;
import org.example.wantedmarket.domain.Product;
import org.example.wantedmarket.status.ProductStatus;

@Getter
@Setter
@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private Integer price;
    private Integer stock;
    private ProductStatus productStatus;
    private Long sellerId;

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .productStatus(product.getProductStatus())
                .sellerId(product.getOwnerId())
                .build();
    }

}

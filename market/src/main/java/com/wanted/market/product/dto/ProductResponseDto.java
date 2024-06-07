package com.wanted.market.product.dto;

import com.wanted.market.member.domain.Member;
import com.wanted.market.order.domain.Order;
import com.wanted.market.product.domain.Product;
import com.wanted.market.product.model.ProductStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

    private Integer id;

    private String name;

    private Integer price;

    private Integer quantity;

    private String status;

    public static ProductResponseDto createFromEntity(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .status(product.getStatus().toString())
                .build();
    }
}

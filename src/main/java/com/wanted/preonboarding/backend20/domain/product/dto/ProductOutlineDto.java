package com.wanted.preonboarding.backend20.domain.product.dto;

import com.wanted.preonboarding.backend20.domain.order.enums.OrderStatus;
import com.wanted.preonboarding.backend20.domain.product.domain.Product;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProductOutlineDto {
    private Long id;
    private String name;
    private int price;
    private String status;

    public static ProductOutlineDto toProductOutlineDto(Product product) {
        return ProductOutlineDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .status(product.getStatus().name())
                .build();
    }

    public void updateProductStatusAsOrderStatus(OrderStatus orderStatus) {
        this.status = orderStatus.name();
    }
}
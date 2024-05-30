package org.example.preonboarding.order.model.payload.response;

import lombok.Builder;
import lombok.Getter;
import org.example.preonboarding.order.model.domain.Order;
import org.example.preonboarding.product.model.mapper.ProductMapper;
import org.example.preonboarding.product.model.payload.response.ProductResponse;

import java.time.LocalDateTime;

@Getter
public class OrderResponse {
    private final Long id;
    private final int totalPrice;
    private final LocalDateTime orderedAt;
    private final ProductResponse product;

    @Builder
    private OrderResponse(Long id, int totalPrice, LocalDateTime orderedAt, ProductResponse product) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.orderedAt = orderedAt;
        this.product = product;
    }

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .orderedAt(order.getOrderedAt())
                .product(ProductMapper.INSTANCE.toProductResponse(order.getProduct()))
                .build();
    }
}

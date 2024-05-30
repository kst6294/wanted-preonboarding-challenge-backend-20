package com.market.wanted.order.dto;

import com.market.wanted.order.entity.OrderStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto {

    private Long orderId;
    private Long sellerId;
    private Long buyerId;
    private Long productId;
    private long price;
    private String productName;
    private OrderStatus orderStatus;

    @QueryProjection
    public OrderDto(Long orderId, Long sellerId, Long buyerId, Long productId, long price, String productName, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.productId = productId;
        this.price = price;
        this.productName = productName;
        this.orderStatus = orderStatus;
    }
}

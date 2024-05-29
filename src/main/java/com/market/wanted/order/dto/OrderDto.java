package com.market.wanted.order.dto;

import com.market.wanted.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDto {

    private Long orderId;
    private Long sellerId;
    private Long buyerId;
    private Long productId;
    private long price;
    private OrderStatus orderStatus;
}

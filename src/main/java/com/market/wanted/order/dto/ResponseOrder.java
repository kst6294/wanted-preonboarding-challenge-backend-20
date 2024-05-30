package com.market.wanted.order.dto;

import com.market.wanted.order.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponseOrder {
    private Long orderId;
    private Long sellerId;
    private Long buyerId;
    private Long productId;
    private String productName;
    private long price;
    private boolean isSeller;
    private OrderStatus orderStatus;
    private LocalDateTime orderDateTime;
}

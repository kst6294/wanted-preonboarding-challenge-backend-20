package org.example.wantedmarket.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.wantedmarket.dto.product.ProductResponse;
import org.example.wantedmarket.dto.user.UserResponse;
import org.example.wantedmarket.domain.Order;
import org.example.wantedmarket.status.OrderStatus;

@Getter
@Setter
@Builder
public class OrderResponse {

    private Long id;
    private Integer quantity;
    private Integer orderedPrice;
    private OrderStatus orderStatus;
    private Long productId;
    private Long buyerId;

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .quantity(order.getQuantity())
                .orderedPrice(order.getOrderedPrice())
                .orderStatus(order.getOrderStatus())
                .productId(order.getProductId())
                .buyerId(order.getBuyerId())
                .build();
    }

}

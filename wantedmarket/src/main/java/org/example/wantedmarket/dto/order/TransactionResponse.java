package org.example.wantedmarket.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.wantedmarket.dto.user.UserResponse;
import org.example.wantedmarket.domain.Order;
import org.example.wantedmarket.status.OrderStatus;

@Getter
@Setter
@Builder
public class TransactionResponse {

    private Long id;
    private Integer quantity;
    private Integer orderPrice;
    private OrderStatus orderStatus;
    private Long buyerId;

    public static TransactionResponse from(Order order) {
        return TransactionResponse.builder()
                .id(order.getId())
                .quantity(order.getQuantity())
                .orderPrice(order.getOrderedPrice())
                .orderStatus(order.getOrderStatus())
                .buyerId(order.getBuyerId())
                .build();
    }

}

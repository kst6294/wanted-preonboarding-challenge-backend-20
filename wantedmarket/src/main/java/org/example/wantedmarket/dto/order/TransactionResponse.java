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
    private Integer confirmedPrice;
    private OrderStatus orderStatus;
    private UserResponse buyer;

    public static TransactionResponse from(Order order) {
        return TransactionResponse.builder()
                .id(order.getId())
                .quantity(order.getQuantity())
                .confirmedPrice(order.getConfirmedPrice())
                .orderStatus(order.getStatus())
                .buyer(UserResponse.from(order.getBuyer()))
                .build();
    }

}

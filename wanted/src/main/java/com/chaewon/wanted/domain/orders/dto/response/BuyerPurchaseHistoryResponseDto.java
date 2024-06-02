package com.chaewon.wanted.domain.orders.dto.response;

import com.chaewon.wanted.domain.orders.entity.OrderStatus;
import com.chaewon.wanted.domain.orders.entity.Orders;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BuyerPurchaseHistoryResponseDto {

    private Long orderId;
    private int orderPrice;
    private OrderStatus orderStatus;

    public static BuyerPurchaseHistoryResponseDto from(Orders orders) {
        return BuyerPurchaseHistoryResponseDto.builder()
                .orderId(orders.getId())
                .orderPrice(orders.getOrderPrice())
                .orderStatus(orders.getOrderStatus())
                .build();
    }

}

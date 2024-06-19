package com.wanted.market.order.dto;

import com.wanted.market.order.domain.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class OrderResponseDto {
    private Integer orderId;
    private Integer productId;
    private String productName;
    private int productPrice;
    private int productQuantity;
    private String productStatus;
    private String memberName;


    public static OrderResponseDto createFromEntity(Order order){
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .productId(order.getProduct().getId())
                .productName(order.getProduct().getName())
                .productPrice(order.getProduct().getPrice())
                .productQuantity(order.getProduct().getQuantity())
                .productStatus(order.getProduct().getStatus().toString())
                .memberName(order.getMember().getName())
                .build();
    }

}

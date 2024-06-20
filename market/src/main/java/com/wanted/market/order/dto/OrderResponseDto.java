package com.wanted.market.order.dto;

import com.wanted.market.member.dto.MemberResponseDto;
import com.wanted.market.order.domain.Order;
import com.wanted.market.order.model.OrderStatus;
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
    private String orderStatus;
    private MemberResponseDto buyer;


    public static OrderResponseDto createFromEntity(Order order){
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .productId(order.getProduct().getId())
                .productName(order.getProduct().getName())
                .productPrice(order.getProduct().getPrice())
                .productQuantity(order.getProduct().getQuantity())
                .productStatus(order.getProduct().getProductStatus().toString())
                .orderStatus(order.getOrderStatus().toString())
                .buyer(MemberResponseDto.createFromEntity(order.getBuyer()))
                .build();
    }

}

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
public class OrderDetailResponseDto {   //order상태 포함

    private Integer orderId;
    private Integer productId;
    private String productName;
    private int productPrice;
    private int productQuantity;
    private String productStatus;
    private String orderStatus;
    private MemberResponseDto buyer;

    public static OrderDetailResponseDto createFromEntity(Order order) {
        return OrderDetailResponseDto.builder()
                .orderId(order.getId())
                .productId(order.getProduct().getId())
                .productName(order.getProduct().getName())
                .productQuantity(order.getProduct().getQuantity())
                .productPrice(order.getProduct().getPrice())
                .productStatus(order.getProduct().getProductStatus().toString())
                .orderStatus(OrderStatus.TRADING.toString())
                .buyer(MemberResponseDto.createFromEntity(order.getBuyer()))
                .build();
    }
}

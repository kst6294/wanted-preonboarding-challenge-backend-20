package com.wanted.market.order.dto;


import com.wanted.market.member.dto.MemberResponseDto;
import com.wanted.market.order.domain.Order;
import com.wanted.market.order.model.OrderStatus;
import com.wanted.market.product.dto.ProductResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDetailResponseDto {   //order상태 포함

    private Integer orderId;
    private ProductResponseDto product;
    private int price;
    private String orderStatus;
    private MemberResponseDto buyer;

    public static OrderDetailResponseDto createFromEntity(Order order) {
        return OrderDetailResponseDto.builder()
                .orderId(order.getId())
                .product(ProductResponseDto.createFromEntity(order.getProduct()))
                .price(order.getProduct().getPrice())
                .orderStatus(OrderStatus.TRADING.toString())
                .buyer(MemberResponseDto.createFromEntity(order.getBuyer()))
                .build();
    }
}

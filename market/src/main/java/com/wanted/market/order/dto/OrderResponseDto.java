package com.wanted.market.order.dto;

import com.wanted.market.member.dto.MemberResponseDto;
import com.wanted.market.order.domain.Order;
import com.wanted.market.product.dto.ProductResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class OrderResponseDto {
    private Integer orderId;
    private ProductResponseDto product;
    private int orderQuantity;
    private int price;
    private String orderStatus;
    private MemberResponseDto buyer;


    public static OrderResponseDto createFromEntity(Order order){
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .product(ProductResponseDto.createFromEntity(order.getProduct()))
                .orderQuantity(order.getOrderQuantity())
                .price(order.getPrice())
                .orderStatus(order.getOrderStatus().toString())
                .buyer(MemberResponseDto.createFromEntity(order.getBuyer()))
                .build();
    }

}

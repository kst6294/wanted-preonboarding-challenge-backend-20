package com.wanted.market.order.dto;


import com.wanted.market.member.dto.MemberResponseDto;
import com.wanted.market.member.repository.MemberRepository;
import com.wanted.market.order.domain.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDetailResponseDto {

    private Integer orderId;
    private Integer productId;
    private String productName;
    private int productPrice;
    private int productQuantity;
    private String productStatus;
    private MemberResponseDto buyer;

    public static OrderDetailResponseDto createFromEntity(Order order) {
        return OrderDetailResponseDto.builder()
                .orderId(order.getId())
                .productId(order.getProduct().getId())
                .productName(order.getProduct().getName())
                .productQuantity(order.getProduct().getQuantity())
                .productPrice(order.getProduct().getPrice())
                .productStatus(order.getProduct().getStatus().toString())
                .buyer(MemberResponseDto.createFromEntity(order.getMember()))
                .build();
    }
}

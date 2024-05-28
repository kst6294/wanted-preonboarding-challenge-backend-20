package com.wanted.preonboarding.backend20.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanted.preonboarding.backend20.domain.member.domain.Member;
import com.wanted.preonboarding.backend20.domain.member.dto.MemberInfoDto;
import com.wanted.preonboarding.backend20.domain.order.domain.Order;
import com.wanted.preonboarding.backend20.domain.order.enums.OrderStatus;
import com.wanted.preonboarding.backend20.domain.product.dto.ProductOutlineDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderInfoDto {
    private Long id;
    private MemberInfoDto sellerInfo;
    private MemberInfoDto buyerInfo;

    private ProductOutlineDto productInfo;

    private OrderStatus sellerOrderStatus;
    private OrderStatus buyerOrderStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    @Builder
    public OrderInfoDto(Order order, Member seller, Member buyer) {
        this.id = order.getId();
        this.sellerInfo = MemberInfoDto.toMemberDto(seller);
        this.buyerInfo = MemberInfoDto.toMemberDto(buyer);
        this.productInfo = ProductOutlineDto.toProductOutlineDto(order.getProduct());
        this.sellerOrderStatus = order.getSellerOrderStatus();
        this.buyerOrderStatus = order.getBuyerOrderStatus();
        this.orderDate = order.getCreatedDate();
    }
}

package com.chaewon.wanted.domain.orders.dto;

import com.chaewon.wanted.domain.member.entity.Member;
import com.chaewon.wanted.domain.orders.entity.OrderStatus;
import com.chaewon.wanted.domain.orders.entity.Orders;
import com.chaewon.wanted.domain.product.entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderRequestDto {

    @NotNull(message = "제품 아이디는 필수입니다.")
    private Long productId;

    @Min(value = 1, message = "가격은 0보다 커야 합니다.")
    private int orderPrice;

    public static Orders from(OrderRequestDto dto, Member member, Product product) {
        return Orders.builder()
                .orderPrice(dto.getOrderPrice())
                .orderStatus(OrderStatus.거래시작)
                .member(member)
                .product(product)
                .build();
    }

}

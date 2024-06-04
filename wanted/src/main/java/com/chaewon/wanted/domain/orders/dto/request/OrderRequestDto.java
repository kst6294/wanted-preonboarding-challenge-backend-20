package com.chaewon.wanted.domain.orders.dto.request;

import com.chaewon.wanted.domain.member.entity.Member;
import com.chaewon.wanted.domain.orders.entity.OrderStatus;
import com.chaewon.wanted.domain.orders.entity.Orders;
import com.chaewon.wanted.domain.product.entity.Product;
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

    public Orders toEntity(Member buyer, Member seller, Product product) {
        return Orders.builder()
                .orderPrice(product.getPrice())
                .orderStatus(OrderStatus.거래시작)
                .buyer(buyer)
                .seller(seller)
                .product(product)
                .build();
    }
}


package com.chaewon.wanted.domain.orders.entity;

import com.chaewon.wanted.common.BaseEntity;
import com.chaewon.wanted.domain.member.entity.Member;
import com.chaewon.wanted.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Orders extends BaseEntity {

    @Column(name = "order_price")
    private int orderPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}

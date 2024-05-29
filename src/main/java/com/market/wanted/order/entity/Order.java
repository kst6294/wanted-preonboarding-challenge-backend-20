package com.market.wanted.order.entity;

import com.market.wanted.common.entity.BaseEntity;
import com.market.wanted.member.entity.Member;
import com.market.wanted.product.entity.ProductStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OrderStatus orderStatus;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member seller;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    public Order(OrderStatus orderStatus, Member seller, OrderItem orderItem) {
        this.orderStatus = orderStatus;
        this.seller = seller;
        this.orderItem = orderItem;
    }

    public void confirm() {
        this.orderStatus = OrderStatus.COMPLETE;
        this.orderItem.getProduct().changeStatus(ProductStatus.SOLD_OUT);
    }
}


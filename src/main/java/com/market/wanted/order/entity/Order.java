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

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "buyer_id")
    private Member buyer;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private OrderItem orderItem;

    public Order(OrderStatus orderStatus, Member buyer, Member seller) {
        this.orderStatus = orderStatus;
        this.seller = seller;
        this.buyer = buyer;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
        orderItem.setOrder(this);

    }

    public void confirm() {
        this.orderStatus = OrderStatus.COMPLETE;
        orderItem.getProduct().changeStatus(ProductStatus.SOLD_OUT);
    }

    public void addBuyer(Member buyer) {
        this.buyer = buyer;
    }
}


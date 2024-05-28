package com.wanted.preonboarding.backend20.domain.order.domain;

import com.wanted.preonboarding.backend20.domain.member.domain.Member;
import com.wanted.preonboarding.backend20.domain.order.enums.OrderStatus;
import com.wanted.preonboarding.backend20.domain.product.domain.Product;
import com.wanted.preonboarding.backend20.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member seller;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member buyer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    @NotNull
    private int price;

    @Enumerated(EnumType.STRING)
    private OrderStatus sellerOrderStatus;

    @Enumerated(EnumType.STRING)
    private OrderStatus buyerOrderStatus;

    @Builder
    public Order(Member seller, Member buyer, Product product) {
        this.seller = seller;
        this.buyer = buyer;
        this.product = product;
        this.price = product.getPrice();
        this.sellerOrderStatus = OrderStatus.PRE;
        this.buyerOrderStatus = OrderStatus.PRE;
    }

    public void sellerApprovedForOrder() {
        this.sellerOrderStatus = OrderStatus.SELLER_APPROVAL;
    }

    public void buyerConfirmsOrder() {
        this.buyerOrderStatus = OrderStatus.BUYER_ORDER_CONFIRM;
    }
}

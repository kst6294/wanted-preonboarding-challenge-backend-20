package com.wanted.preonboarding.module.order.entity;


import com.wanted.preonboarding.module.common.entity.BaseEntity;
import com.wanted.preonboarding.module.exception.order.InvalidOrderException;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.entity.Users;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "ORDERS")
@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SELLER_ID", nullable = false)
    private Users seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUYER_ID", nullable = false)
    private Users buyer;

    @Enumerated(EnumType.STRING)
    @Column(name = "ORDER_STATUS", nullable = false)
    private OrderStatus orderStatus;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderHistory> orderHistories = new LinkedHashSet<>();


    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private OrderProductSnapShot productSnapShot;


    public void setBuyer(Users buyer) {
        if (this.seller.equals(buyer)) {
            throw new InvalidOrderException();
        }
        this.buyer = buyer;
    }

    public void changeOrderStatus(OrderStatus newStatus) {
        if (this.orderStatus != newStatus) {
            this.orderStatus = newStatus;
            addOrderHistory(newStatus);
        }
    }

    private void addOrderHistory(OrderStatus orderStatus) {
        OrderHistory orderHistory = OrderHistory.builder()
                .order(this)
                .orderStatus(orderStatus)
                .build();
        this.orderHistories.add(orderHistory);
    }

    public void createProductSnapShot() {
        this.productSnapShot = OrderProductSnapShot.fromProduct(this.product, this);
    }

}

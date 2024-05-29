package com.example.wanted.order.infrastructure;

import com.example.wanted.order.domain.OrderStatus;
import com.example.wanted.product.domain.Product;
import com.example.wanted.product.infrastructure.ProductEntity;
import com.example.wanted.user.infrastucture.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "ORDERS")
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private UserEntity seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private UserEntity buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    public OrderEntity(UserEntity seller, UserEntity buyer, ProductEntity product, int price) {
        this.seller = seller;
        this.buyer = buyer;
        this.price = price;
        this.product = product;
        this.orderStatus = OrderStatus.REQUEST;
    }
}

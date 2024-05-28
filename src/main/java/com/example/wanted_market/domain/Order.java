package com.example.wanted_market.domain;

import com.example.wanted_market.type.EOrderStatus;
import com.example.wanted_market.type.ERole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EOrderStatus status;

    @Builder
    public Order(Product product, User buyer, EOrderStatus status){
        this.product = product;
        this.buyer = buyer;
        this.status = status;
    }
}

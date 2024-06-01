package com.wanted.demo.domain.statements.entity;

import com.wanted.demo.domain.baseEntity.BaseEntity;
import com.wanted.demo.domain.product.entity.Product;
import com.wanted.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "statements")
public class Statements extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private boolean purchaseStatus;

    @Column(nullable = false)
    private boolean sellStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @Builder
    public Statements(Long price, boolean purchaseStatus, boolean sellStatus, User user, Product product){
        this.price = price;
        this.purchaseStatus = purchaseStatus;
        this.sellStatus = sellStatus;
        this.user = user;
        this.product = product;
    }


}

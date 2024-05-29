package com.example.wanted.product.infrastructure;

import com.example.wanted.product.domain.ProductSellingStatus;
import com.example.wanted.user.infrastucture.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "PRODUCTS")
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductSellingStatus sellingStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private UserEntity seller;

    public ProductEntity(String name, int price, int quantity, UserEntity seller) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.sellingStatus = ProductSellingStatus.SELLING;
        this.seller = seller;
    }
}

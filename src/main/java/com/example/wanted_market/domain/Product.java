package com.example.wanted_market.domain;

import com.example.wanted_market.type.EProductStatus;
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
@Table(name = "productes")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EProductStatus status;

    @Builder
    public Product(User seller, String name, int price, EProductStatus status){
        this.seller = seller;
        this.name = name;
        this.price = price;
        this.status = status;
    }
}

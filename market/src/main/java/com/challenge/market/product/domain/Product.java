package com.challenge.market.product.domain;

import com.challenge.market.product.domain.constant.ProductStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int price;
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name="product_status")
    private ProductStatus productStatus;

    public Product(int price, String name, ProductStatus productStatus) {
        this.price = price;
        this.name = name;
        this.productStatus = productStatus;
    }
}

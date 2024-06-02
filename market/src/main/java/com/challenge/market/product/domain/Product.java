package com.challenge.market.product.domain;

import com.challenge.market.product.domain.constant.ProductStatus;
import com.challenge.market.product.dto.ProductResponse;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
@Builder
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

    public Product(Long id, int price, String name, ProductStatus productStatus) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.productStatus = productStatus;
    }

    public static ProductResponse of(Product product){
        return ProductResponse.builder()
                .id(product.id)
                .name(product.name)
                .price(product.price)
                .productStatus(product.productStatus)
                .build();
    }
}

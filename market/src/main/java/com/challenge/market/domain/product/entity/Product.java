package com.challenge.market.domain.product.entity;

import com.challenge.market.web.product.dto.ProductResponse;
import com.challenge.market.domain.product.constant.ProductStatus;
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
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .productStatus(product.getProductStatus())
                .build();
    }
}

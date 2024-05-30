package com.sunyesle.wanted_market.entity;

import com.sunyesle.wanted_market.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer price;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;
}

package com.wanted.market.product.domain;


import com.wanted.market.product.model.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer price;
    @Enumerated(EnumType.STRING)
    @Column
    private ProductStatus status;
}

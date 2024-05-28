package com.wanted.market.product.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    private Long id;
    private String name;
    private Long price;
    private ProductStatus status;

    private Product(String name, Long price, ProductStatus status){
       this.name = name;
       this.price = price;
       this.status = status;
    }

    public static Product of(String name, Long price){
        return new Product(name, price, ProductStatus.ONSALE);
    }

    public void changeStatus(ProductStatus status){
        this.status = status;
    }
}

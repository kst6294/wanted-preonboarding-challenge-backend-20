package com.wanted.market.product.domain;

import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
public class Product {

    private Long id;
    private String name;
    private Long price;

    public Product(String name, Long price){
       this.name = name;
       this.price = price;
    }
}

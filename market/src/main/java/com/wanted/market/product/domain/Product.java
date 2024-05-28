package com.wanted.market.product.domain;

import com.wanted.market.user.domain.User;
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
    private Long sellerId;
    private Long buyerId;

    private Product(String name, Long price, ProductStatus status, Long sellerId){
       this.name = name;
       this.price = price;
       this.status = status;
       this.sellerId = sellerId;
    }

    public static Product of(String name, Long price, Long sellerId){
        return new Product(name, price, ProductStatus.ONSALE, sellerId);
    }

    public void changeStatus(ProductStatus status){
        this.status = status;
    }

    public void changeBuyerId(Long buyerId){
        this.buyerId = buyerId;
    }
}

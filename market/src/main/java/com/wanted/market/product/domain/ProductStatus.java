package com.wanted.market.product.domain;

import com.wanted.market.global.exception.InvalidProductStatusException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {
    ONSALE(0, "On Sale"),
    RESERVED(1, "Reserved"),
    SOLD(2, "Sold");

    private final int value;
    private final String name;

    public static ProductStatus from (String name) {
        for(ProductStatus productStatus : ProductStatus.values()){
            if(productStatus.getName().equals(name))
                return productStatus;
        }
        throw new InvalidProductStatusException();
    }

    public static ProductStatus from (int value) {
        for(ProductStatus productStatus : ProductStatus.values()){
            if(productStatus.getValue() == value)
                return productStatus;
        }
        throw new InvalidProductStatusException();
    }

    public String getName(){ return this.name; }
    public int getValue(){ return this.value; }
}

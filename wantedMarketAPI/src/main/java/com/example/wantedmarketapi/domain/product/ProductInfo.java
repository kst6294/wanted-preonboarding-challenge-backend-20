package com.example.wantedmarketapi.domain.product;

import com.example.wantedmarketapi.domain.product.Product.Status;
import lombok.Getter;

@Getter
public class ProductInfo {
    private final Long id;
    private final String name;
    private final Integer price;
    private final Status status;
    private final Long userId;

    public ProductInfo(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.status = product.getStatus();
        this.userId = product.getUserId();
    }


}

package com.exception_study.global.fixture.product;

import com.exception_study.api.product.entity.Product;
import com.exception_study.api.user_account.entity.UserAccount;

public class ProductFixture {


    public static Product get(Long id, String name, int price, String status, int quantity, UserAccount seller){
        Product entity = new Product();
        entity.setId(id);
        entity.setName(name);
        entity.setPrice(price);
        entity.setStatus(status);
        entity.setQuantity(quantity);
        entity.setSeller(seller);
        return entity;
    }
}

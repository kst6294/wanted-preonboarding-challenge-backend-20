package com.exception_study.global.fixture.product_order;

import com.exception_study.api.product.entity.Product;
import com.exception_study.api.product_order.entity.ProductOrder;
import com.exception_study.api.user_account.entity.UserAccount;

public class ProductOrderFixture {


    public static ProductOrder get(Long id,
                              Product product,
                               int price,
                              UserAccount seller,
                              UserAccount buyer,
                              String sellerStatus,
                              String buyerStatus
                              ){
        ProductOrder entity = new ProductOrder();
        entity.setId(id);
        entity.setProduct(product);
        entity.setPrice(price);
        entity.setSeller(seller);
        entity.setBuyer(buyer);
        entity.setSellerStatus(sellerStatus);
        entity.setBuyerStatus(buyerStatus);
        return entity;
    }
}

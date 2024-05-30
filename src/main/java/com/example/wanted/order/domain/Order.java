package com.example.wanted.order.domain;

import com.example.wanted.product.domain.Product;
import com.example.wanted.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Order {
    private Long id;
    private User seller;
    private User buyer;
    private int price;
    private Product product;
    private OrderStatus orderStatus;

    @Builder
    public Order(Long id, User seller, User buyer, Product product,int price, OrderStatus orderStatus) {
        this.id = id;
        this.seller = seller;
        this.buyer = buyer;
        this.price = price;
        this.product = product;
        this.orderStatus = orderStatus;
    }

    public static Order from(User buyer, Product product) {
        return Order.builder()
                .seller(product.getSeller())
                .buyer(buyer)
                .product(product)
                .price(product.getPrice())
                .orderStatus(OrderStatus.REQUEST)
                .build();
    }
}

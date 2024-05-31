package com.example.wanted.order.domain;

import com.example.wanted.module.exception.ResourceNotFoundException;
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
    private OrderStatus status;

    @Builder
    public Order(Long id, User seller, User buyer, Product product,int price, OrderStatus status) {
        this.id = id;
        this.seller = seller;
        this.buyer = buyer;
        this.price = price;
        this.product = product;
        this.status = status;
    }

    public static Order from(User buyer, Product product) {
        return Order.builder()
                .seller(product.getSeller())
                .buyer(buyer)
                .product(product)
                .price(product.getPrice())
                .status(OrderStatus.REQUEST)
                .build();
    }

    //todo: 에러처리
    public void approve() {
        if (!status.equals(OrderStatus.REQUEST)) {
            throw new ResourceNotFoundException("Product", 1L);
        }
        this.status = OrderStatus.APPROVAL;
    }

    public boolean checkSeller(User user) {
        return this.seller.getId().equals(user.getId());
    }

    public boolean checkBuyer(User user) {
        return this.buyer.getId().equals(user.getId());
    }
}

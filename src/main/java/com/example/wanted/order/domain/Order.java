package com.example.wanted.order.domain;

import com.example.wanted.module.exception.ResourceNotFoundException;
import com.example.wanted.product.domain.Product;
import com.example.wanted.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
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
        if(product.checkSeller(buyer)) {
            throw new IllegalArgumentException("판매자는 구매요청을 할 수 없습니다.");
        }

        return Order.builder()
                .seller(product.getSeller())
                .buyer(buyer)
                .product(product)
                .price(product.getPrice())
                .status(OrderStatus.REQUEST)
                .build();
    }

    //todo: 에러처리
    public void approve(User user) {
        if(!checkSeller(user)) {
            log.warn("User {}는 Order {}의 판매자가 아닙니다.");
            throw new IllegalArgumentException("판매자만 승인할 수 있습니다.");
        }

        if (!status.equals(OrderStatus.REQUEST)) {
            throw new IllegalStateException("상태가 주문 요청이 아닙니다.");
        }
        this.status = OrderStatus.APPROVAL;
    }

    public boolean checkSeller(User user) {
        return this.seller.getId().equals(user.getId());
    }

    public boolean checkBuyer(User user) {
        return this.buyer.getId().equals(user.getId());
    }

    public boolean checkProduct(Product product) {
        return this.product.getId().equals(product.getId());
    }
}

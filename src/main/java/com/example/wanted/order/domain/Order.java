package com.example.wanted.order.domain;

import com.example.wanted.module.exception.InvalidOrderStatusException;
import com.example.wanted.module.exception.OrderConfirmationNotAllowedException;
import com.example.wanted.module.exception.ResourceNotFoundException;
import com.example.wanted.module.exception.SellerCannotMakeOrderException;
import com.example.wanted.product.domain.Product;
import com.example.wanted.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;

import javax.security.sasl.AuthenticationException;

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
            throw new SellerCannotMakeOrderException("판매자는 구매요청을 할 수 없습니다.");
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
            log.warn("User {}는 Order {}의 판매자가 아닙니다.",user.getId(), this.id);
            throw new AccessDeniedException("권한이 없습니다.");
        }

        if (!status.equals(OrderStatus.REQUEST)) {
            throw new InvalidOrderStatusException("주문 요청 상태가 아닙니다.");
        }
        this.status = OrderStatus.APPROVAL;
    }

    public void complete(User user, Product product) {
        if(!checkBuyer(user)) {
            log.warn("User {}는 Order {}의 구매자가 아닙니다.",user.getId(), this.id);
            throw new AccessDeniedException("권한이 없습니다");
        }

        if (!status.equals(OrderStatus.APPROVAL)) {
            throw new InvalidOrderStatusException("주문 요청 상태가 아닙니다.");
        }
        this.product = product;
        this.status = OrderStatus.PURCHASE_CONFIRMATION;
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

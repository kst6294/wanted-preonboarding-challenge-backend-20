package com.example.wanted.order.service.response;

import com.example.wanted.order.domain.Order;
import com.example.wanted.order.domain.OrderStatus;
import com.example.wanted.product.domain.Product;
import com.example.wanted.product.service.response.ProductResponse;
import com.example.wanted.user.controller.response.UserResponse;
import com.example.wanted.user.domain.User;
import com.example.wanted.user.service.port.UserRepository;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderResponse {
    private Long id;
    private UserResponse seller;
    private UserResponse buyer;
    private int price;
    private Long productId;
    private String productName;
    private OrderStatus status;

    @Builder
    public OrderResponse(Long id, UserResponse seller, UserResponse buyer, int price, ProductResponse product, OrderStatus status) {
        this.id = id;
        this.seller = seller;
        this.buyer = buyer;
        this.price = price;
        this.productId = product.getId();
        this.productName = product.getName();
        this.status = status;
    }

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .seller(UserResponse.from(order.getSeller()))
                .buyer(UserResponse.from(order.getBuyer()))
                .price(order.getPrice())
                .product(ProductResponse.from(order.getProduct()))
                .status(order.getStatus())
                .build();
    }

}

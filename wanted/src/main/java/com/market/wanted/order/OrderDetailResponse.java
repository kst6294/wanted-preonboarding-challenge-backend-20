package com.market.wanted.order;

import lombok.Data;

@Data
public class OrderDetailResponse {
    private Long id;
    private String name;
    private Long price;
    private String sellerUsername;
    private String buyerUsername;
    private OrderStatus status;

    public OrderDetailResponse(Order order) {
        this.id = order.getId();
        this.name = order.getItemName();
        this.price = order.getItemPrice();
        this.status = order.getStatus();
        this.sellerUsername = order.getSeller().getUsername();
        this.buyerUsername = order.getBuyer().getUsername();
    }
}

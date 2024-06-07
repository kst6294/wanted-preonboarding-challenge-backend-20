package com.wanted.market.product.ui.dto.response;

public class OrderInfo {
    private Long id;
    private Long buyerId;
    private String buyerName;
    private Integer price;
    private String status;

    public Long getId() {
        return id;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public Integer getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }
}

package com.wanted.market.order.ui.dto.response;

public class SimpleOrderInfoResponse {
    private Long id;
    private Long buyerId;
    private String buyerName;
    private Long productId;
    private String productName;
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

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }
}

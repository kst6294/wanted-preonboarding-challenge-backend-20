package com.wanted.market.product.ui.dto.response;

public class SimpleProductInfoResponse {
    private Long id;
    private String name;
    private Integer price;
    private String status;
    private Integer version;

    public SimpleProductInfoResponse(Long id, String name, Integer price, String status, Integer version) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public Integer getVersion() {
        return version;
    }
}

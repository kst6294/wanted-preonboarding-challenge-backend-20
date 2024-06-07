package com.wanted.market.product.ui.dto.response;

import java.util.Collections;
import java.util.List;

public class DetailProductInfoResponse extends SimpleProductInfoResponse {
    private List<OrderInfo> orders;

    public DetailProductInfoResponse(Long id, String name, Integer price, String status, Integer version) {
        super(id, name, price, status, version);
    }

    public List<OrderInfo> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderInfo> orders) {
        this.orders = orders;
    }

    public static DetailProductInfoResponse createWithEmptyOrders(SimpleProductInfoResponse info) {
        DetailProductInfoResponse response = new DetailProductInfoResponse(info.getId(), info.getName(), info.getPrice(), info.getStatus(), info.getVersion());
        response.setOrders(Collections.emptyList());
        return response;
    }
}

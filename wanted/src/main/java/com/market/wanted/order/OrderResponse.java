package com.market.wanted.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {
    private List<OrderDetailResponse> list;
    private int count;

    public OrderResponse(List<Order> list){
        this.count = list.size();
        this.list = list.stream().map(i -> new OrderDetailResponse(i)).toList();
    }
}

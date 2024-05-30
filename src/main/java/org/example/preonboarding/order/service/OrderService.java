package org.example.preonboarding.order.service;

import org.example.preonboarding.order.model.payload.request.OrderCreateRequest;
import org.example.preonboarding.order.model.payload.response.OrderResponse;

import java.time.LocalDateTime;

public interface OrderService {
    OrderResponse createOrder(OrderCreateRequest request, LocalDateTime orderedAt);

    OrderResponse approveOrder(int orderId);
}

package org.example.preonboarding.order.service;

import org.example.preonboarding.order.model.payload.request.OrderCreateRequest;
import org.example.preonboarding.order.model.payload.request.OrderSearchRequest;
import org.example.preonboarding.order.model.payload.response.OrderResponse;
import org.example.preonboarding.product.model.payload.response.ProductResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderCreateRequest request, LocalDateTime orderedAt);

    OrderResponse approveOrder(long orderId);

    List<OrderResponse> searchOrder(OrderSearchRequest orderSearchRequest);

    OrderResponse completeOrder(long orderId);
}

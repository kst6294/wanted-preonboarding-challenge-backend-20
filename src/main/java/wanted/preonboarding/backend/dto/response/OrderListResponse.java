package wanted.preonboarding.backend.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import wanted.preonboarding.backend.domain.entity.Orders;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderListResponse {

    List<OrderResponse> orders;

    public static OrderListResponse from(List<Orders> orders) {
        List<OrderResponse> orderList = new ArrayList<>();
        orders.forEach((order) -> orderList.add(OrderResponse.from(order)));
        return new OrderListResponse(orderList);
    }
}

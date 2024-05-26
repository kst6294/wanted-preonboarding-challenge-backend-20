package wanted.preonboarding.backend.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.preonboarding.backend.domain.entity.Orders;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResponse {

    private String itemName;
    private int totalPrice;
    private LocalDateTime createdAt;

    public static OrderResponse from(Orders orders) {
        return new OrderResponse(orders.getItem().getName(), orders.getPrice(), orders.getCreatedAt());
    }
}

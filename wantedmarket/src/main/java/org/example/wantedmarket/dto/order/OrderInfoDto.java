package org.example.wantedmarket.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.wantedmarket.status.OrderStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDto {
    private Long orderId;
    private Integer confirmedPrice;
    private Long productId;
    private Long sellerId;
    private Long buyerId;
    private OrderStatus orderStatus;
}

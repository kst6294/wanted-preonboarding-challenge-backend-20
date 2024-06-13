package org.example.wantedmarket.dto.order;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.example.wantedmarket.dto.product.ProductCreateDto;
import org.example.wantedmarket.model.Order;
import org.example.wantedmarket.model.Product;
import org.example.wantedmarket.status.OrderStatus;

@Getter
@Setter
public class OrderCreateDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotNull
        private Long productId;
        @NotNull
        private Integer quantity;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long orderId;
        private OrderStatus orderStatus;

        public static OrderCreateDto.Response from(Order order) {
            return Response.builder()
                    .orderId(order.getId())
                    .orderStatus(order.getStatus())
                    .build();
        }
    }

}

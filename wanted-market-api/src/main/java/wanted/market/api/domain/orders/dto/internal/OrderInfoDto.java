package wanted.market.api.domain.orders.dto.internal;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.market.api.domain.orders.entity.Order;
import wanted.market.api.domain.product.dto.internal.ProductInfoDto;
import wanted.market.api.domain.user.dto.internal.UserInfoDto;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderInfoDto {

    private Long id;
    private Long price;
    private LocalDateTime orderTime;
    private Long count;
    private String status;
    private ProductInfoDto product;

    @Builder
    public OrderInfoDto(Long id, Long price, LocalDateTime orderTime, Long count, String status, ProductInfoDto product) {
        this.id = id;
        this.price = price;
        this.orderTime = orderTime;
        this.count = count;
        this.status = status;
        this.product = product;
    }

    public static OrderInfoDto from(Order order, ProductInfoDto product) {
        return OrderInfoDto.builder()
                .id(order.getId())
                .price(order.getPrice())
                .orderTime(order.getOrderTime())
                .count(order.getCount())
                .status(order.getStatus().getStatus())
                .product(product)
                .build();
    }
}

package wanted.market.api.domain.orders.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.market.api.domain.orders.dto.internal.OrderInfoDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderListResponseDto {

    private List<OrderInfoDto> orderInfo;

    @Builder
    public OrderListResponseDto(List<OrderInfoDto> orderInfo) {
        this.orderInfo = orderInfo;
    }
}

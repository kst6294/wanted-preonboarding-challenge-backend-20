package wanted.market.api.domain.orders.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApproveOrderRequestDto {
    private Long orderId;
}

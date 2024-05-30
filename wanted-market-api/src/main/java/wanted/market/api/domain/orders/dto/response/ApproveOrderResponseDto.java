package wanted.market.api.domain.orders.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApproveOrderResponseDto {

    private Long orderId;

    @Builder
    public ApproveOrderResponseDto(Long orderId) {
        this.orderId = orderId;
    }
}

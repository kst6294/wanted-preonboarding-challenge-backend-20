package wanted.market.api.domain.orders.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApproveOrderResponseDto {

    private Long orderId;

    @Builder
    private ApproveOrderResponseDto(Long orderId) {
        this.orderId = orderId;
    }

    public static ApproveOrderResponseDto of(Long orderId){
        return ApproveOrderResponseDto.builder().orderId(orderId).build();
    }
}

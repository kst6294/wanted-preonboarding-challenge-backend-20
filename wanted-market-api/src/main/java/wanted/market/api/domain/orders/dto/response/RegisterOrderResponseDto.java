package wanted.market.api.domain.orders.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterOrderResponseDto {

    private Long orderId;

    @Builder
    public RegisterOrderResponseDto(Long orderId) {
        this.orderId = orderId;
    }
}

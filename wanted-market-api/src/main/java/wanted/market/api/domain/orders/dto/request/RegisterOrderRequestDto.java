package wanted.market.api.domain.orders.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterOrderRequestDto {
    private Long userId;
    private Long price;
    private Long productId;
}

package wanted.preonboarding.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderStatusUpdateRequest {

    private Long itemId;
    private Long orderId;
}

package wanted.preonboarding.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderSaveRequest {

    private Long itemId;
    private int price;
}

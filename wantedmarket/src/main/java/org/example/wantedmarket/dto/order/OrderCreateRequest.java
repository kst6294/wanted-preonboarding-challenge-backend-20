package org.example.wantedmarket.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateRequest {

    @NotNull(message = "주문할 제품의 id를 입력해주세요.")
    private Long productId;

    @NotNull(message = "주문할 제품의 개수를 입력해주세요.")
    @Positive(message = "1개 이상부터 주문할 수 있습니다.")
    private Integer quantity;

}

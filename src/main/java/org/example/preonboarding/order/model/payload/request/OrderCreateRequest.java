package org.example.preonboarding.order.model.payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {
    @NotEmpty(message = "상품 번호는 필수입니다.")
    private String productNumber;

    @Builder
    private OrderCreateRequest(String productNumber) {
        this.productNumber = productNumber;
    }
}

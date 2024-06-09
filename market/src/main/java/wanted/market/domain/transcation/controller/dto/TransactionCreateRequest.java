package wanted.market.domain.transcation.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.market.domain.transcation.service.dto.request.TransactionCreateServiceRequest;

@Getter
@NoArgsConstructor
public class TransactionCreateRequest {
    private Long productId;
    private int price;

    @Builder
    private TransactionCreateRequest(Long productId, int price) {
        this.productId = productId;
        this.price = price;
    }

    public TransactionCreateServiceRequest toService(String email) {
        return TransactionCreateServiceRequest.builder()
                .email(email)
                .productId(productId)
                .price(price)
                .build();
    }
}

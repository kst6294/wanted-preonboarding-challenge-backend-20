package wanted.market.domain.transcation.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.market.domain.transcation.service.dto.request.TransactionCancelServiceRequest;

@Getter
@NoArgsConstructor
public class TransactionCancelRequest {
    private Long transactionId;

    @Builder
    private TransactionCancelRequest(Long transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionCancelServiceRequest toService(String email) {
        return TransactionCancelServiceRequest.builder()
                .email(email)
                .transactionId(transactionId)
                .build();
    }
}

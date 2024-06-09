package wanted.market.domain.transcation.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import wanted.market.domain.transcation.repository.entity.Transaction;

@Getter
public class TransactionCreateResponse {

    private Long transactionId;

    @Builder
    public TransactionCreateResponse(Long transactionId) {
        this.transactionId = transactionId;
    }


    public static TransactionCreateResponse of(Transaction transaction) {
        return TransactionCreateResponse.builder()
                .transactionId(transaction.getId())
                .build();
    }
}

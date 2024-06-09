package wanted.market.domain.transcation.service.dto.request;

import lombok.Builder;
import lombok.Getter;
import wanted.market.domain.member.repository.entity.Member;
import wanted.market.domain.product.repository.entity.Product;
import wanted.market.domain.transcation.repository.entity.Transaction;

import static wanted.market.domain.transcation.repository.entity.TransactionStatus.TRADE_CANCEL;

@Getter
public class TransactionCancelServiceRequest {

    private String email;

    private Long transactionId;


    @Builder
    private TransactionCancelServiceRequest(String email, Long transactionId) {
        this.email = email;
        this.transactionId = transactionId;
    }

    public Transaction toTransaction(Member member, Product product) {
        return Transaction.builder()
                .member(member)
                .transactionStatus(TRADE_CANCEL)
                .product(product)
                .build();
    }
}

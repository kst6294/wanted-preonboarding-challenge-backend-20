package wanted.market.domain.transcation.service.dto.request;

import lombok.Builder;
import lombok.Getter;
import wanted.market.domain.member.repository.entity.Member;
import wanted.market.domain.product.repository.entity.Product;
import wanted.market.domain.transcation.repository.entity.Transaction;
import wanted.market.domain.transcation.repository.entity.TransactionStatus;

import static wanted.market.domain.transcation.repository.entity.TransactionStatus.*;

@Getter
public class TransactionCreateServiceRequest {

    private String email;

    private Long productId;

    private int price;

    @Builder
    private TransactionCreateServiceRequest(String email, Long productId, int price) {
        this.email = email;
        this.productId = productId;
        this.price = price;
    }

    public Transaction toTransaction(Member member, Product product) {
        return Transaction.builder()
                .member(member)
                .transactionStatus(TRADING)
                .product(product)
                .price(price)
                .build();
    }
}

package wanted.market.domain.transcation.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import wanted.market.domain.product.repository.entity.Product;
import wanted.market.domain.product.service.dto.response.ProductListResponse;
import wanted.market.domain.transcation.repository.entity.Transaction;
import wanted.market.domain.transcation.repository.entity.TransactionStatus;

@Getter
public class TransactionListResponse {
    private String name;
    private int price;
    private TransactionStatus transactionStatus;

    @Builder
    private TransactionListResponse(String name, int price, TransactionStatus transactionStatus) {
        this.name = name;
        this.price = price;
        this.transactionStatus = transactionStatus;
    }

    public static TransactionListResponse of(Transaction transaction) {
        return TransactionListResponse.builder()
                .name(transaction.getProduct().getName())
                .price(transaction.getPrice())
                .transactionStatus(transaction.getTransactionStatus())
                .build();
    }
}

package market.market.domain.transaction.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueryTransactionListResponse {
    private Long transactionId;
    private Long productId;
    private String productName;
    private int price;
}

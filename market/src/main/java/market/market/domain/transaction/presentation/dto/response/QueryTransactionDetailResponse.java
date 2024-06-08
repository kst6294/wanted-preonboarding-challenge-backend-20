package market.market.domain.transaction.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueryTransactionDetailResponse {
    private Long transactionId;
    private Long buyerId;
    private String buyerName;
    private Long sellerId;
    private String sellerName;
    private int price;
    private String status;
}

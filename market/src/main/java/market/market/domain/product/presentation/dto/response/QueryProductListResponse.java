package market.market.domain.product.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class QueryProductListResponse {
    private Long productId;

    private String name; // 제품명

    private int price; // 가격

    private String status; // 제품 상태

    private LocalDate createdAt;
}

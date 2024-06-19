package market.market.domain.product.presentation.dto.requset;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import market.market.domain.product.enums.Status;

@Getter
public class CreateProduct {

    @Schema(description = "제품명", nullable = false)
    private String name; // 제품명

    @Schema(description = "제품 가격", nullable = false)
    private int price; // 가격

    // 2단계 추가
    @Schema(description = "제품 수량", nullable = false)
    private int quantity; // 가격
}

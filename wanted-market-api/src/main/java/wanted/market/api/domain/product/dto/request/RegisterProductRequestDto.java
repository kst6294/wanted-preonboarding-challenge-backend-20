package wanted.market.api.domain.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RegisterProductRequestDto {

    private Long userId;
    private Long price;
    private Long count;
    private String productName;

    @Builder
    public RegisterProductRequestDto(Long userId, Long price, Long count, String productName) {
        this.userId = userId;
        this.price = price;
        this.count = count;
        this.productName = productName;
    }
}

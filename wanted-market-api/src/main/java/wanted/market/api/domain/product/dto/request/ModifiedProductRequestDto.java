package wanted.market.api.domain.product.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ModifiedProductRequestDto {
    private Long productId;
    private Long price;

}

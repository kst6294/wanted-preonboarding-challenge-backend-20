package wanted.market.api.domain.product.dto.response;

import lombok.Builder;
import lombok.Getter;
import wanted.market.api.domain.product.dto.internal.ProductInfoDto;

@Getter
public class ProductDetailResponseDto {
    private final ProductInfoDto product;

    @Builder
    public ProductDetailResponseDto(ProductInfoDto product){
        this.product = product;
    }
}

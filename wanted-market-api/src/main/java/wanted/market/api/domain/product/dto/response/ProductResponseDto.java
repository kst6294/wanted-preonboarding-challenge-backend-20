package wanted.market.api.domain.product.dto.response;

import lombok.Builder;
import lombok.Getter;
import wanted.market.api.domain.product.dto.internal.ProductInfoDto;
import wanted.market.api.domain.user.dto.internal.UserInfoDto;

@Getter
public class ProductResponseDto {
    private final ProductInfoDto product;
    private final UserInfoDto user;

    @Builder
    public ProductResponseDto(ProductInfoDto product, UserInfoDto user){
        this.product = product;
        this.user = user;
    }
}

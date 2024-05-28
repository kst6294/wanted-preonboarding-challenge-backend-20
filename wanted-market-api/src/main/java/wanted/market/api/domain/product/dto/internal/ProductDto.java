package wanted.market.api.domain.product.dto.internal;

import lombok.Builder;
import lombok.Getter;
import wanted.market.api.domain.user.dto.internal.UserInfoDto;

@Getter
public class ProductDto {
    private final ProductInfoDto product;
    private final UserInfoDto user;

    @Builder
    public ProductDto(ProductInfoDto product, UserInfoDto user){
        this.product = product;
        this.user = user;
    }
}

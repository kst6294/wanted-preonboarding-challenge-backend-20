package wanted.market.api.domain.product.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.market.api.global.response.dto.internal.PageInfoDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductPageResponseDto {
    PageInfoDto page;
    List<ProductResponseDto> products;

    @Builder
    public ProductPageResponseDto(PageInfoDto page, List<ProductResponseDto> products) {
        this.page = page;
        this.products = products;
    }
}

package wanted.market.api.domain.product.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.market.api.domain.product.dto.internal.ProductDto;
import wanted.market.api.global.response.dto.internal.PageInfoDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductResponseDto {
    PageInfoDto page;
    List<ProductDto> products;

    @Builder
    public ProductResponseDto(PageInfoDto page, List<ProductDto> products) {
        this.page = page;
        this.products = products;
    }
}

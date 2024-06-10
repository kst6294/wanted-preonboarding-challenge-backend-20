package wanted.market.api.domain.product.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.market.api.domain.product.dto.internal.ProductInfoDto;
import wanted.market.api.global.response.dto.internal.PageInfoDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductPageResponseDto {
    private PageInfoDto page;
    private List<ProductInfoDto> products;

    @Builder
    public ProductPageResponseDto(PageInfoDto page, List<ProductInfoDto> products) {
        this.page = page;
        this.products = products;
    }
    public static ProductPageResponseDto of(PageInfoDto page, List<ProductInfoDto> products){
        return ProductPageResponseDto.builder()
                .page(page)
                .products(products)
                .build();
    }
}

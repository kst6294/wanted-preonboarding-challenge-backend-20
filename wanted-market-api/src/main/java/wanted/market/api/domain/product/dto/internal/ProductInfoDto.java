package wanted.market.api.domain.product.dto.internal;

import lombok.Builder;
import lombok.Getter;
import wanted.market.api.domain.product.entity.Product;

@Getter
public class ProductInfoDto {

    private Long id;
    private String name;
    private Long price;
    private Long count;
    private String status;

    @Builder
    private ProductInfoDto(Long id, String name, Long price, Long count, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
        this.status = status;
    }

    public static ProductInfoDto fromProduct(Product product){
        return ProductInfoDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .count(product.getCount())
                .status(product.getStatus().getStatus())
                .build();
    }
}

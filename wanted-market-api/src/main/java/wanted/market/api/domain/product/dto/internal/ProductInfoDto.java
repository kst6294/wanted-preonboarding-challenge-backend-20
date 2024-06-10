package wanted.market.api.domain.product.dto.internal;

import lombok.Builder;
import lombok.Getter;
import wanted.market.api.domain.product.entity.Product;
import wanted.market.api.domain.user.dto.internal.UserInfoDto;

@Getter
public class ProductInfoDto {

    private Long id;
    private String name;
    private Long price;
    private Long count;
    private String status;
    private UserInfoDto seller;

    @Builder
    private ProductInfoDto(Long id, String name, Long price, Long count, String status, UserInfoDto user) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
        this.status = status;
        this.seller = user;
    }

    public static ProductInfoDto from(Product product){
        return ProductInfoDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .count(product.getCount())
                .status(product.getStatus().getStatus())
                .user(UserInfoDto.from(product))
                .build();
    }
}

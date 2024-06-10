package wanted.market.api.domain.product.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ModifiedProductResponseDto {

    private Long productId;
    private Long beforePrice;
    private Long afterPrice;

    @Builder
    public ModifiedProductResponseDto(Long productId, Long beforePrice, Long afterPrice) {
        this.productId = productId;
        this.beforePrice = beforePrice;
        this.afterPrice = afterPrice;
    }

    public static ModifiedProductResponseDto of(Long productId, Long beforePrice, Long afterPrice){
        return ModifiedProductResponseDto.builder()
                .productId(productId)
                .beforePrice(beforePrice)
                .afterPrice(afterPrice)
                .build();
    }
}

package wanted.market.api.domain.product.dto.response;


import lombok.Builder;
import lombok.Getter;
@Getter
public class RegisterProductResponseDto {

    private final Long productId;

    @Builder
    private RegisterProductResponseDto(Long productId){
        this.productId = productId;
    }

    public static RegisterProductResponseDto of(Long productId){
        return RegisterProductResponseDto.builder().productId(productId).build();
    }
}

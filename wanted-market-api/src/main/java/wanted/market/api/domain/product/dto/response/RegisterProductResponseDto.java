package wanted.market.api.domain.product.dto.response;


import lombok.Builder;
import lombok.Getter;
@Getter
public class RegisterProductResponseDto {

    private final Long productId;

    @Builder
    public RegisterProductResponseDto(Long productId){
        this.productId = productId;
    }
}

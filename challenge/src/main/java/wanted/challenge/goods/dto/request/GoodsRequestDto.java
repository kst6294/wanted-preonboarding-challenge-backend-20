package wanted.challenge.goods.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GoodsRequestDto {

    public record CreateGoods (
            @NotBlank(message = "상품명을 입력해주세요")
            String name,
            @Min(value = 1, message = "가격은 0원보다 커야 합니다")
            @NotNull(message = "가격을 입력해주세요")
            Integer price,
            @Min(value = 1, message = "수량은 0개보다 많아야 합니다")
            @NotNull(message = "수량을 입력해주세요")
            Integer quantity){
    }
}

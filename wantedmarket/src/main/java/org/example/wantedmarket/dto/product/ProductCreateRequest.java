package org.example.wantedmarket.dto.product;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductCreateRequest {

    @NotBlank(message = "제품 이름을 입력해주세요.")
    @Size(min = 1, max = 20, message = "최소 1자, 최대 20자로 입력해주세요.")
    private String name;

    @NotNull(message = "제품 가격을 입력해주세요.")
    @Positive(message = "가격은 0원보다 커야 합니다.")
    private Integer price;

    @NotNull(message = "제품 개수를 입력해주세요.")
    @Positive(message = "재고는 1개 이상이어야 합니다.")
    private Integer quantity;

}

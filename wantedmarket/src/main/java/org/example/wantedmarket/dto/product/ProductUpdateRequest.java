package org.example.wantedmarket.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateRequest {

    @NotNull(message = "제품 id를 입력해주세요.")
    private Long id;

    @NotNull(message = "수정할 제품의 가격을 입력해주세요.")
    @Positive(message = "가격은 0원보다 커야 합니다.")
    private Integer price;

}

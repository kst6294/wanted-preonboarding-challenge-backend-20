package com.chaewon.wanted.domain.product.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifyRequestDto {

    @Min(value = 1, message = "가격은 0보다 커야 합니다.")
    private int price;

}

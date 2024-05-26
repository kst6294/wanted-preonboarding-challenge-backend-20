package com.wanted.preonboarding.module.product.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateProduct {

    @NotBlank(message = "상품명은 필수 항목입니다.")
    @Length(max = 100, message = "상품명은 100자를 넘어갈 수 없습니다.")
    private String productName;

    @Min(value = 100, message = "상품 가격은 최소 0원 이상입니다.")
    @Max(value = 100000000, message = "상품 가격은 최대 100,000,000원 이하입니다." )
    private long price;

}

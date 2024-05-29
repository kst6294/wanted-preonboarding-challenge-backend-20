package com.wanted.preonboarding.backend20.domain.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    @NotBlank
    private String name;

    @Min(1)
    private int price;

    @NotBlank
    private String description;

    @Min(1)
    private int totalQuantity;
}

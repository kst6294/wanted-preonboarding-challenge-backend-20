package com.chaewon.wanted.domain.product.dto.request;

import com.chaewon.wanted.domain.member.entity.Member;
import com.chaewon.wanted.domain.product.entity.Product;
import com.chaewon.wanted.domain.product.entity.ProductStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterRequestDto {
    @NotBlank(message = "제품 이름은 필수입니다.")
    private String name;

    @Min(value = 1, message = "가격은 0보다 커야 합니다.")
    private int price;

    @Min(value = 1, message = "수량은 0보다 커야 합니다.")
    private int quentity;

    public static Product from(RegisterRequestDto registrationDto, Member member) {
        return Product.builder()
                .name(registrationDto.getName())
                .price(registrationDto.getPrice())
                .quantity(registrationDto.getQuentity())
                .productStatus(ProductStatus.판매중)
                .member(member)
                .build();
    }
}

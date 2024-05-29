package com.wanted.preonboarding.module.product.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wanted.preonboarding.module.user.core.UserContext;
import com.wanted.preonboarding.module.user.entity.Users;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateProduct implements UserContext {

    @NotBlank(message = "상품명은 필수 항목입니다.")
    @Length(max = 100, message = "상품명은 100자를 넘어갈 수 없습니다.")
    private String productName;

    @Min(value = 100, message = "상품 가격은 최소 0원 이상입니다.")
    @Max(value = 100000000, message = "상품 가격은 최대 100,000,000원 이하입니다." )
    private long price;

    @Min(value = 1, message = "재고는 최소 1개 이상입니다.")
    @Max(value = 9000, message = "상품 재고는 최대 9000개 입니다." )
    private int quantity;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Setter
    private Users users;

}

package com.wanted.preonboarding.module.order.dto;

import com.wanted.preonboarding.module.order.validator.ValidOrder;
import com.wanted.preonboarding.module.order.validator.ValidProductStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ValidProductStatus
@ValidOrder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateOrder {

    @NotNull(message = "상품 아이디는 필수입니다")
    @Min(value = 1, message = "상품 아이디는 1 이상입니다.")
    long productId;

}

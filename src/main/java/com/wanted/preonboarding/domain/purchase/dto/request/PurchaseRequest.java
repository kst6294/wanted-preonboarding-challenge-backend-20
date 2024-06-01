package com.wanted.preonboarding.domain.purchase.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PurchaseRequest {

    @NotNull(message = "상품 아이디는 필수 사항입니다.")
    @Min(value = 0, message = "상품 아이디는 0 이상이여야 합니다.")
    private Long id;
}

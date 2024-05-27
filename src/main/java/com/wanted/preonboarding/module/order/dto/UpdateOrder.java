package com.wanted.preonboarding.module.order.dto;

import com.wanted.preonboarding.module.order.enums.OrderStatus;
import com.wanted.preonboarding.module.order.validator.ValidOrderStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ValidOrderStatus
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateOrder {

    @NotNull(message = "주문 아이디는 필수입니다")
    @Min(value = 1, message = "주문 아이디는 1 이상입니다.")
    long orderId;

    @NotNull(message = "주문 상태는 필수 값 입니다.")
    OrderStatus orderStatus;


}

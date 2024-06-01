package org.example.preonboarding.order.model.payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.preonboarding.member.model.entity.Member;
import org.example.preonboarding.order.model.enums.OrderDivision;

@Getter
@NoArgsConstructor
public class OrderSearchRequest {
    @NotEmpty(message = "주문구분은 필수입니다.")
    private OrderDivision orderDivision;

    private Member member;

    @Builder
    private OrderSearchRequest(OrderDivision orderDivision, Member member) {
        this.orderDivision = orderDivision;
        this.member = member;
    }
}

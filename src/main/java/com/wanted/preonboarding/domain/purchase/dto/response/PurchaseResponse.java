package com.wanted.preonboarding.domain.purchase.dto.response;

import com.wanted.preonboarding.domain.product.entity.Product;
import com.wanted.preonboarding.domain.purchase.entity.Purchase;
import com.wanted.preonboarding.domain.purchase.entity.PurchaseState;
import com.wanted.preonboarding.domain.user.dto.response.UserResponse;
import com.wanted.preonboarding.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;


@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PurchaseResponse {
    private Long id; // 아이디
    private UserResponse buyer; // 구매자
    private Integer price; // 구매당시 가격
    private PurchaseState state; // 상태

    public static PurchaseResponse of(Purchase purchase) {
        return PurchaseResponse.builder()
                .id(purchase.getId())
                .buyer(UserResponse.of(purchase.getUser()))
                .price(purchase.getPrice())
                .state(purchase.getState())
                .build();
    }
}

package com.wanted.wantedpreonboardingchallengebackend20.domain.product.dto.request;

import com.wanted.wantedpreonboardingchallengebackend20.domain.product.entity.ProductState;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegistProductRequestDto {
    private String name;
    private Integer price;
    private Integer quantity;
    private ProductState state;
}

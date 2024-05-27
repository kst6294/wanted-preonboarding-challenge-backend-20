package com.wanted.preonboarding.module.product.dto;

import com.wanted.preonboarding.module.common.enums.OrderType;
import com.wanted.preonboarding.module.product.filter.ItemFilter;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductFilter implements ItemFilter {

    @Setter
    private Long lastDomainId;
    @Setter
    private String cursorValue;

    @Setter
    private OrderType orderType;
    private Long lowestPrice;
    private Long highestPrice;

    @Setter
    private String email;

}

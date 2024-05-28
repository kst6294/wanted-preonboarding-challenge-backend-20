package com.wanted.preonboarding.module.product.filter;

import com.wanted.preonboarding.module.common.enums.OrderType;
import com.wanted.preonboarding.module.common.filter.ItemFilter;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductFilter implements ItemFilter {

    private Long lastDomainId;

    private OrderType orderType;

}

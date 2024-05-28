package com.wanted.preonboarding.module.order.filter;

import com.wanted.preonboarding.module.common.enums.OrderType;
import com.wanted.preonboarding.module.common.filter.ItemFilter;
import com.wanted.preonboarding.module.order.enums.UserRole;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderFilter implements ItemFilter {

    private Long lastDomainId;
    private UserRole userRole;
    @Setter
    private OrderType orderType;

}

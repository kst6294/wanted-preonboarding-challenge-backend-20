package com.wanted.preonboarding.module.order.core;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import com.wanted.preonboarding.module.utils.SecurityUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailedOrderContext extends BaseOrderContext{

    private long price;
    private String productName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insertDate;

    private Boolean isCurrentUserSeller;

    @QueryProjection
    public DetailedOrderContext(long orderId, long productId, String buyer, String seller, OrderStatus orderStatus, long price, String productName, LocalDateTime insertDate) {
        super(orderId, productId, buyer, seller, orderStatus);
        this.price = price;
        this.productName = productName;
        this.insertDate = insertDate;
        this.isCurrentUserSeller = getSeller().equals(SecurityUtils.currentUserEmail());
    }

}

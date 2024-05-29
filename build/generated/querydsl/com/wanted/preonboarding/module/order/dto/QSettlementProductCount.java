package com.wanted.preonboarding.module.order.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.wanted.preonboarding.module.order.dto.QSettlementProductCount is a Querydsl Projection type for SettlementProductCount
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QSettlementProductCount extends ConstructorExpression<SettlementProductCount> {

    private static final long serialVersionUID = 450591352L;

    public QSettlementProductCount(com.querydsl.core.types.Expression<Integer> quantity, com.querydsl.core.types.Expression<Long> orderedOrCompletedProductCount) {
        super(SettlementProductCount.class, new Class<?>[]{int.class, long.class}, quantity, orderedOrCompletedProductCount);
    }

}


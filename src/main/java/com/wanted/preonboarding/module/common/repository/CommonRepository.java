package com.wanted.preonboarding.module.common.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.wanted.preonboarding.module.common.enums.OrderType;

import java.util.List;

public interface CommonRepository {

    List<OrderSpecifier<?>> createOrderSpecifiersFromPageable(EntityPathBase<?> entityPath, OrderType orderType);

}

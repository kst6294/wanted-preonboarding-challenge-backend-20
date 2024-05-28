package com.wanted.preonboarding.module.common.repository;


import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.wanted.preonboarding.module.common.enums.OrderType;
import com.wanted.preonboarding.module.common.filter.ItemFilter;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;

import static com.wanted.preonboarding.module.product.entity.QProduct.product;

@Repository
public abstract class AbstractCommonRepository implements CommonRepository {

    private static final String ID_FIELD = "id";

    public List<OrderSpecifier<?>> createOrderSpecifiersFromPageable(EntityPathBase<?> entityPath, OrderType orderType) {
        List<OrderSpecifier<?>> orderSpecifiers = new LinkedList<>();
        if (orderType != null) {
            Order direction = orderType.isAscending() ? Order.ASC : Order.DESC;
            String field = orderType.getField();

            orderSpecifiers.add(getSortedColumn(direction, entityPath, field));
            orderSpecifiers.add(getSortedColumn(Order.DESC, entityPath, ID_FIELD));
        } else {
            orderSpecifiers.add(getSortedColumn(Order.DESC, entityPath, ID_FIELD));
        }
        return orderSpecifiers;
    }

    protected OrderSpecifier<?> getSortedColumn(Order order, Path<?> parent, String fieldName) {
        Path<Object> fieldPath = Expressions.path(Object.class, parent, fieldName);
        return new OrderSpecifier(order, fieldPath);
    }


    protected BooleanExpression createDynamicProductGroupIdCondition(ItemFilter filter, NumberPath<Long> idPath) {
        Long lastDomainId = filter.getLastDomainId();
        if (lastDomainId == null) return null;

        boolean isAscending = filter.getOrderType() != null && filter.getOrderType().isAscending();
        return isAscending ? idPath.goe(lastDomainId) : idPath.loe(lastDomainId);
    }


}


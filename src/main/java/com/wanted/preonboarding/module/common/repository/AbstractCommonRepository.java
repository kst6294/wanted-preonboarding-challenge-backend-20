package com.wanted.preonboarding.module.common.repository;


import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.wanted.preonboarding.module.common.enums.OrderType;
import com.wanted.preonboarding.module.product.filter.ItemFilter;
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



    protected BooleanExpression createDynamicWhereCondition(ItemFilter filter, OrderType orderType) {
        String cursorValue = filter.getCursorValue();
        Long lastDomainId = filter.getLastDomainId();

        if (StringUtils.hasText(cursorValue) && lastDomainId != null) {
            return createBaseCondition(orderType, cursorValue, lastDomainId);
        }
        return createDynamicProductGroupIdCondition(filter);
    }

    private BooleanExpression createBaseCondition(OrderType orderType, String cursorValue, Long lastDomainId) {
        BooleanExpression idCondition = product.id.loe(lastDomainId);

        return switch (orderType) {
            case HIGH_PRICE -> product.price.lt(parseToLong(cursorValue))
                    .or(product.price.eq(parseToLong(cursorValue)).and(idCondition));
            case LOW_PRICE -> product.price.gt(parseToLong(cursorValue))
                    .or(product.price.eq(parseToLong(cursorValue)).and(idCondition));
            case PAST -> product.id.goe(lastDomainId);
            default -> throw new IllegalArgumentException("지원하지 않는 정렬 유형입니다.");
        };
    }

    protected BooleanExpression createDynamicProductGroupIdCondition(ItemFilter filter) {
        Long lastDomainId = filter.getLastDomainId();
        if (lastDomainId == null) return null;

        boolean isAscending = filter.getOrderType() != null && filter.getOrderType().isAscending();
        return isAscending ? product.id.goe(lastDomainId) : product.id.loe(lastDomainId);
    }

    private Long parseToLong(String value) {
        return Long.parseLong(value);
    }
}


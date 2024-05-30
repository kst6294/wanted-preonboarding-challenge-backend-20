package com.wanted.market_api.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.market_api.constant.CustomerIdentity;
import com.wanted.market_api.entity.Order;
import com.wanted.market_api.entity.QOrder;
import com.wanted.market_api.entity.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.wanted.market_api.entity.QOrder.order;
import static com.wanted.market_api.entity.QProduct.product;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public Page<Order> getOrders(Pageable pageable,
                                 CustomerIdentity customerIdentity,
                                 Long memberId) {
        List<Order> orders = jpaQueryFactory
                .selectFrom(order)
                .leftJoin(order.product, product).fetchJoin()
                .where(
                        buyerOrSellerIdEq(customerIdentity, memberId)
                )
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(Wildcard.count)
                .from(order)
                .where(
                        buyerOrSellerIdEq(customerIdentity, memberId)
                );

        return PageableExecutionUtils.getPage(orders, pageable, () -> {
            return countQuery.fetch().get(0);
        });
    }

    private BooleanExpression buyerOrSellerIdEq(CustomerIdentity customerIdentity, Long memberId) {
        return customerIdentity.equals(CustomerIdentity.BUYER)
                ? order.buyerId.eq(memberId)
                : order.sellerId.eq(memberId);
    }
}

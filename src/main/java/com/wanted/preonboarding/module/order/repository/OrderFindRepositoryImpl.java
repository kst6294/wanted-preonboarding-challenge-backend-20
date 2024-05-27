package com.wanted.preonboarding.module.order.repository;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.user.entity.QUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.wanted.preonboarding.module.order.entity.QOrder.order;
import static com.wanted.preonboarding.module.user.entity.QUsers.users;

@Repository
@RequiredArgsConstructor
public class OrderFindRepositoryImpl implements OrderFindRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Order> fetchOrderEntity(long orderId) {
        QUsers seller = new QUsers("seller");
        QUsers buyer = new QUsers("buyer");

        return Optional.ofNullable(
                queryFactory.selectFrom(order)
                        .innerJoin(order.seller, seller).fetchJoin()
                        .innerJoin(order.buyer, buyer).fetchJoin()
                        .where(orderIdEq(orderId))
                        .fetchOne()
        );
    }


    private BooleanExpression orderIdEq(long orderId) {
        return order.id.eq(orderId);
    }
}

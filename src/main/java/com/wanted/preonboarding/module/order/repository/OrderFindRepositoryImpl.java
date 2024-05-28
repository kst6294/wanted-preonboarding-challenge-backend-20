package com.wanted.preonboarding.module.order.repository;


import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.preonboarding.module.common.repository.AbstractCommonRepository;
import com.wanted.preonboarding.module.order.core.DetailedOrderContext;
import com.wanted.preonboarding.module.order.core.QDetailedOrderContext;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.enums.UserRole;
import com.wanted.preonboarding.module.order.filter.OrderFilter;
import com.wanted.preonboarding.module.user.entity.QUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.wanted.preonboarding.module.order.entity.QOrder.order;
import static com.wanted.preonboarding.module.order.entity.QOrderHistory.orderHistory;
import static com.wanted.preonboarding.module.order.entity.QOrderProductSnapShot.orderProductSnapShot;

@Repository
@RequiredArgsConstructor
public class OrderFindRepositoryImpl extends AbstractCommonRepository implements OrderFindRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DetailedOrderContext> fetchOrderDetail(long orderId, String email) {
        QUsers seller = new QUsers("seller");
        QUsers buyer = new QUsers("buyer");

        return queryFactory
                .select(
                        new QDetailedOrderContext(
                                order.id,
                                orderProductSnapShot.productId,
                                buyer.email,
                                seller.email,
                                orderHistory.orderStatus,
                                orderProductSnapShot.price,
                                orderProductSnapShot.productName,
                                orderHistory.insertDate
                        )
                )
                .from(order)
                .innerJoin(order.orderHistories, orderHistory)
                .innerJoin(order.productSnapShot, orderProductSnapShot)
                .innerJoin(order.seller, seller)
                .innerJoin(order.buyer, buyer)
                .where(orderIdEq(orderId), emailEq(email))
                .orderBy(orderHistory.id.asc())
                .fetch();
    }

    @Override
    public List<DetailedOrderContext> fetchOrderDetails(OrderFilter filter, String email, Pageable pageable) {
        QUsers seller = new QUsers("seller");
        QUsers buyer = new QUsers("buyer");

        List<OrderSpecifier<?>> orders = createOrderSpecifiersFromPageable(order, filter.getOrderType());
        BooleanExpression dynamicWhere = createDynamicProductGroupIdCondition(filter, order.id);

        return queryFactory
                .select(
                        new QDetailedOrderContext(
                                order.id,
                                orderProductSnapShot.productId,
                                buyer.email,
                                seller.email,
                                order.orderStatus,
                                orderProductSnapShot.price,
                                orderProductSnapShot.productName,
                                order.insertDate
                        )
                )
                .from(order)
                .innerJoin(order.productSnapShot, orderProductSnapShot)
                .innerJoin(order.seller, seller)
                .innerJoin(order.buyer, buyer)
                .where(dynamicWhere, userRoleTypeEq(filter, email))
                .orderBy(orders.toArray(OrderSpecifier[]::new))
                .limit(pageable.getPageSize() + 1)
                .fetch();
    }


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

    private BooleanExpression emailEq(String email) {
        return order.seller.email.eq(email).or(order.buyer.email.eq(email));
    }

    private BooleanExpression userRoleTypeEq(OrderFilter filter, String email) {
        UserRole userRole = filter.getUserRole();
        if(userRole != null){
            if(userRole.isSeller()) return order.seller.email.eq(email);
            else return order.buyer.email.eq(email);
        }

        return emailEq(email);
    }

}

package com.wanted.preonboarding.module.order.repository;


import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.preonboarding.module.common.repository.AbstractCommonRepository;
import com.wanted.preonboarding.module.order.core.DetailedOrderContext;
import com.wanted.preonboarding.module.order.core.QDetailedOrderContext;
import com.wanted.preonboarding.module.order.dto.QSettlementProductCount;
import com.wanted.preonboarding.module.order.dto.SettlementProductCount;
import com.wanted.preonboarding.module.order.entity.Order;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
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
import static com.wanted.preonboarding.module.product.entity.QProduct.product;

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
                                orderProductSnapShot.product.id,
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
                                orderProductSnapShot.product.id,
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

    @Override
    public boolean hasPurchaseHistory(long productId, String email) {
        Long l = queryFactory
                .select(orderProductSnapShot.id)
                .from(orderProductSnapShot)
                .innerJoin(orderProductSnapShot.order, order)
                .where(productIdEq(productId), buyerEmailEq(email))
                .fetchOne();

        return l != null;
    }

    @Override
    public Optional<SettlementProductCount> fetchSettlementProductCount(long productId) {
        return Optional.ofNullable(queryFactory
                .select(
                        new QSettlementProductCount(
                                product.quantity,
                                orderProductSnapShot.count()
                        )
                )
                .from(orderProductSnapShot)
                .innerJoin(orderProductSnapShot.order, order)
                .innerJoin(orderProductSnapShot.product, product)
                .where(productIdEq(productId), orderOrderedOrCompleted())
                .groupBy(product.id, orderProductSnapShot.id)
                .fetchOne());
    }


    private BooleanExpression orderIdEq(long orderId) {
        return order.id.eq(orderId);
    }

    private BooleanExpression productIdEq(long productId){
        return orderProductSnapShot.product.id.eq(productId);
    }

    private BooleanExpression orderOrderedOrCompleted() {
        return order.orderStatus.eq(OrderStatus.ORDERED).or(order.orderStatus.eq(OrderStatus.COMPLETED));
    }

    protected BooleanExpression buyerEmailEq(String buyerEmail){
        return order.buyer.email.eq(buyerEmail);
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

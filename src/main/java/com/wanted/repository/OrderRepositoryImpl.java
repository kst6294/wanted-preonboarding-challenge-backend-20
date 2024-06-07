package com.wanted.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.dto.PurchaseDto;
import com.wanted.dto.QPurchaseDto;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.wanted.entity.QMember.member;
import static com.wanted.entity.QOrder.order;
import static com.wanted.entity.QOrderProduct.orderProduct;
import static com.wanted.entity.QProduct.product;

public class OrderRepositoryImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PurchaseDto> findAllPurchaseDtos() {
        return queryFactory
                .select(new QPurchaseDto(
                        order.id.as("order_id"),
                        member.email,
                        product.name,
                        orderProduct.count,
                        product.price,
                        Expressions.numberTemplate(Long.class, "{0} * {1}", orderProduct.count, product.price).as("totalPrice"),
                        order.status.stringValue().as("status")))
                .from(order)
                .join(order.member,member)
                .join(order.orderProducts, orderProduct)
                .join(orderProduct.product, product)
                .fetch();
    }
}

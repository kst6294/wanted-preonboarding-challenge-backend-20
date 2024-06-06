package com.market.wanted.order.repository;

import com.market.wanted.member.entity.QMember;
import com.market.wanted.order.dto.OrderDto;
import com.market.wanted.order.dto.QOrderDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.market.wanted.order.entity.QOrder.order;
import static com.market.wanted.order.entity.QOrderItem.orderItem;
import static com.market.wanted.product.entity.QProduct.product;

@Repository
public class OrderFindRepositoryImpl implements OrderFindRepository{

    private final JPAQueryFactory queryFactory;

    public OrderFindRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<OrderDto> findAllBySellerEmail(String username) {
        QMember seller = new QMember("seller");
        return queryFactory
                .select(new QOrderDto(order.id.as("orderId"),
                        order.seller.id.as("sellerId"),
                        order.buyer.id.as("buyerId"),
                        product.id.as("productId"),
                        orderItem.price,
                        product.productName,
                        order.orderStatus
                        ))
                .from(order)
                .leftJoin(order.orderItem, orderItem)
                .leftJoin(orderItem.product, product)
                .leftJoin(product.seller, seller)
                .where(order.seller.username.eq(username))
                .fetch();
    }

    @Override
    public List<OrderDto> findAllByBuyerEmail(String username) {
        QMember seller = new QMember("seller");
        return queryFactory
                .select(new QOrderDto(order.id.as("orderId"),
                        order.seller.id.as("sellerId"),
                        order.buyer.id.as("buyerId"),
                        product.id.as("productId"),
                        orderItem.price,
                        product.productName,
                        order.orderStatus
                ))
                .from(order)
                .leftJoin(order.orderItem, orderItem)
                .leftJoin(orderItem.product, product)
                .leftJoin(product.seller, seller)
                .where(order.buyer.username.eq(username))
                .fetch();
    }
}

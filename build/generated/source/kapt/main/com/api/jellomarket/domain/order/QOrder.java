package com.api.jellomarket.domain.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -2033909277L;

    public static final QOrder order = new QOrder("order1");

    public final StringPath createdAt = createString("createdAt");

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final EnumPath<com.api.jellomarket.enums.order.OrderStatus> status = createEnum("status", com.api.jellomarket.enums.order.OrderStatus.class);

    public final NumberPath<Float> totalAmount = createNumber("totalAmount", Float.class);

    public final StringPath updatedAt = createString("updatedAt");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QOrder(String variable) {
        super(Order.class, forVariable(variable));
    }

    public QOrder(Path<Order> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrder(PathMetadata metadata) {
        super(Order.class, metadata);
    }

}


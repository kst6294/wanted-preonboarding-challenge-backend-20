package com.wanted.preonboarding.module.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderProductSnapShot is a Querydsl query type for OrderProductSnapShot
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderProductSnapShot extends EntityPathBase<OrderProductSnapShot> {

    private static final long serialVersionUID = -763683888L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderProductSnapShot orderProductSnapShot = new QOrderProductSnapShot("orderProductSnapShot");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QOrder order;

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final StringPath productName = createString("productName");

    public QOrderProductSnapShot(String variable) {
        this(OrderProductSnapShot.class, forVariable(variable), INITS);
    }

    public QOrderProductSnapShot(Path<? extends OrderProductSnapShot> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderProductSnapShot(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderProductSnapShot(PathMetadata metadata, PathInits inits) {
        this(OrderProductSnapShot.class, metadata, inits);
    }

    public QOrderProductSnapShot(Class<? extends OrderProductSnapShot> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new QOrder(forProperty("order"), inits.get("order")) : null;
    }

}


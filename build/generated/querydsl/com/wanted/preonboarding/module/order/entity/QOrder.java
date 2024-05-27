package com.wanted.preonboarding.module.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = 1031691683L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final com.wanted.preonboarding.module.common.entity.QBaseEntity _super = new com.wanted.preonboarding.module.common.entity.QBaseEntity(this);

    public final com.wanted.preonboarding.module.user.entity.QUsers buyer;

    //inherited
    public final EnumPath<com.wanted.preonboarding.module.common.enums.Yn> deleteYn = _super.deleteYn;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertDate = _super.insertDate;

    //inherited
    public final StringPath insertOperator = _super.insertOperator;

    public final EnumPath<com.wanted.preonboarding.module.order.enums.OrderStatus> orderStatus = createEnum("orderStatus", com.wanted.preonboarding.module.order.enums.OrderStatus.class);

    public final com.wanted.preonboarding.module.product.entity.QProduct product;

    public final com.wanted.preonboarding.module.user.entity.QUsers seller;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    //inherited
    public final StringPath updateOperator = _super.updateOperator;

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.buyer = inits.isInitialized("buyer") ? new com.wanted.preonboarding.module.user.entity.QUsers(forProperty("buyer")) : null;
        this.product = inits.isInitialized("product") ? new com.wanted.preonboarding.module.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
        this.seller = inits.isInitialized("seller") ? new com.wanted.preonboarding.module.user.entity.QUsers(forProperty("seller")) : null;
    }

}


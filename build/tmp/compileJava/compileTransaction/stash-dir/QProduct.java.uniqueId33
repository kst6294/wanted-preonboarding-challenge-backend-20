package com.wanted.preonboarding.module.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 2120097347L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final com.wanted.preonboarding.module.common.entity.QBaseEntity _super = new com.wanted.preonboarding.module.common.entity.QBaseEntity(this);

    //inherited
    public final EnumPath<com.wanted.preonboarding.module.common.enums.Yn> deleteYn = _super.deleteYn;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertDate = _super.insertDate;

    //inherited
    public final StringPath insertOperator = _super.insertOperator;

    public final SetPath<com.wanted.preonboarding.module.order.entity.OrderProductSnapShot, com.wanted.preonboarding.module.order.entity.QOrderProductSnapShot> orderProductSnapShots = this.<com.wanted.preonboarding.module.order.entity.OrderProductSnapShot, com.wanted.preonboarding.module.order.entity.QOrderProductSnapShot>createSet("orderProductSnapShots", com.wanted.preonboarding.module.order.entity.OrderProductSnapShot.class, com.wanted.preonboarding.module.order.entity.QOrderProductSnapShot.class, PathInits.DIRECT2);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final StringPath productName = createString("productName");

    public final EnumPath<com.wanted.preonboarding.module.product.enums.ProductStatus> productStatus = createEnum("productStatus", com.wanted.preonboarding.module.product.enums.ProductStatus.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final com.wanted.preonboarding.module.user.entity.QUsers seller;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    //inherited
    public final StringPath updateOperator = _super.updateOperator;

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.seller = inits.isInitialized("seller") ? new com.wanted.preonboarding.module.user.entity.QUsers(forProperty("seller")) : null;
    }

}


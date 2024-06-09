package com.api.jellomarket.domain.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 1691638181L;

    public static final QProduct product = new QProduct("product");

    public final StringPath createdAt = createString("createdAt");

    public final StringPath deletedAt = createString("deletedAt");

    public final StringPath description = createString("description");

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Long> sellerId = createNumber("sellerId", Long.class);

    public final EnumPath<com.api.jellomarket.enums.product.ProductState> state = createEnum("state", com.api.jellomarket.enums.product.ProductState.class);

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public final StringPath updatedAt = createString("updatedAt");

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}


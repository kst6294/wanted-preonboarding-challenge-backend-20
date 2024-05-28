package com.wanted.preonboarding.module.product.repository;


import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.preonboarding.module.common.filter.ItemFilter;
import com.wanted.preonboarding.module.common.repository.AbstractCommonRepository;
import com.wanted.preonboarding.module.product.core.BaseSku;
import com.wanted.preonboarding.module.product.core.QBaseSku;
import com.wanted.preonboarding.module.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.wanted.preonboarding.module.product.entity.QProduct.product;
import static com.wanted.preonboarding.module.user.entity.QUsers.users;

@Repository
@RequiredArgsConstructor
public class ProductFindRepositoryImpl extends AbstractCommonRepository implements ProductFindRepository{

    private final JPAQueryFactory queryFactory;


    @Override
    public Optional<BaseSku> fetchBaseSku(long productId) {
        return Optional.ofNullable(
                queryFactory.select(
                        new QBaseSku(
                                product.id,
                                product.productName,
                                product.price,
                                product.productStatus,
                                users.email
                        ))
                        .from(product)
                        .innerJoin(product.seller, users)
                        .where(productIdEq(productId))
                        .fetchOne()
        );
    }

    @Override
    public Optional<Product> fetchProductEntity(long productId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(product)
                        .innerJoin(product.seller, users)
                        .fetchJoin()
                        .where(productIdEq(productId))
                        .fetchOne()
        );
    }

    @Override
    public List<BaseSku> fetchBaseSkus(ItemFilter filter, Pageable pageable) {

        List<OrderSpecifier<?>> orders = createOrderSpecifiersFromPageable(product, filter.getOrderType());
        BooleanExpression dynamicWhere = createDynamicProductGroupIdCondition(filter, product.id);

        return queryFactory.select(
                        new QBaseSku(
                                product.id,
                                product.productName,
                                product.price,
                                product.productStatus,
                                users.email
                        ))
                .from(product)
                .innerJoin(product.seller, users)
                .where(
                        dynamicWhere
                    )
                .orderBy(orders.toArray(OrderSpecifier[]::new))
                .limit(pageable.getPageSize() + 1)
                .fetch();
    }


    private BooleanExpression productIdEq(long productId){
        return product.id.eq(productId);
    }

}

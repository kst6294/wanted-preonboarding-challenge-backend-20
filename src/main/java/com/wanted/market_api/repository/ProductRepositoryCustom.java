package com.wanted.market_api.repository;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.market_api.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.wanted.market_api.entity.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public Page<Product> findAllProducts(Pageable pageable) {
        List<Product> products = jpaQueryFactory.selectFrom(product)
                .where()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(Wildcard.count)
                .from(product)
                .where();

        return PageableExecutionUtils.getPage(products, pageable, () -> {
            return countQuery.fetch().get(0);
        });
    }
}


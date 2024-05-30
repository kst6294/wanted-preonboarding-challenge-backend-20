package com.wanted.challenge.product.repository;

import static com.wanted.challenge.product.entity.QProduct.product;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.challenge.product.response.ProductPreviewResponse;
import com.wanted.challenge.product.response.QProductPreviewResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<ProductPreviewResponse> retrieveProductsPreview(Pageable pageable) {
        List<ProductPreviewResponse> content = jpaQueryFactory
                .select(new QProductPreviewResponse(
                        product.name,
                        product.price,
                        product.reservation
                ))
                .from(product)

                .orderBy(product.id.desc())

                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())

                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(product.count())
                .from(product);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}

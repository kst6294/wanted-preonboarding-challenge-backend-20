package com.wanted.challenge.product.repository;

import static com.wanted.challenge.product.entity.QProduct.product;
import static com.wanted.challenge.transact.entity.QTransact.transact;
import static com.wanted.challenge.transact.entity.QTransactLog.transactLog;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.challenge.product.model.Reservation;
import com.wanted.challenge.product.response.ProductPreviewResponse;
import com.wanted.challenge.product.response.PurchaseProductResponse;
import com.wanted.challenge.product.response.QProductPreviewResponse;
import com.wanted.challenge.product.response.QPurchaseProductResponse;
import com.wanted.challenge.product.response.QReserveProductResponse;
import com.wanted.challenge.product.response.ReserveProductResponse;
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

    public Page<PurchaseProductResponse> retrievePurchaseProducts(Long buyerId, Pageable pageable) {
        List<PurchaseProductResponse> content = jpaQueryFactory
                .select(new QPurchaseProductResponse(
                        product.id,
                        product.reservation,
                        product.name,
                        transact.price,
                        transactLog.transactState
                ))
                .from(product)

                .innerJoin(transact)
                .on(transact.product.eq(product))

                .innerJoin(transactLog)
                .on(transactLog.id.eq(lastTransactLogId()))

                .where(transact.buyer.id.eq(buyerId))

                .orderBy(transact.id.desc())

                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())

                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(product.count())
                .from(product)

                .innerJoin(transact)
                .on(transact.product.eq(product))

                .innerJoin(transactLog)
                .on(transactLog.id.eq(lastTransactLogId()))

                .where(transact.buyer.id.eq(buyerId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private static JPQLQuery<Long> lastTransactLogId() {
        return JPAExpressions
                .select(transactLog.id.max())
                .from(transact)

                .innerJoin(transactLog)
                .on(transactLog.transact.eq(transact))

                .where(transact.product.eq(product));
    }

    public Page<ReserveProductResponse> retrieveReserveProducts(Long sellerId, Pageable pageable) {
        List<ReserveProductResponse> content = jpaQueryFactory
                .select(new QReserveProductResponse(
                        product.id,
                        product.name,
                        product.price
                ))
                .from(product)

                .where(product.reservation.eq(Reservation.RESERVE),
                        product.seller.id.eq(sellerId))

                .orderBy(product.id.desc())

                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())

                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(product.count())
                .from(product)

                .where(product.reservation.eq(Reservation.RESERVE));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}

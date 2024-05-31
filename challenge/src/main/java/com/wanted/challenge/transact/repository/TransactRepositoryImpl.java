package com.wanted.challenge.transact.repository;

import static com.wanted.challenge.product.entity.QPurchase.purchase;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.transact.model.TransactDetail;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransactRepositoryImpl implements TransactRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public TransactDetail retrieveLastTransactDetail(Long buyerId, Long productId) {
        return jpaQueryFactory
                .select(purchase.purchaseDetail)
                .from(purchase)
                .where(purchase.id.eq(maxPurchaseId(buyerId, productId)))
                .fetchOne();
    }

    private static JPQLQuery<Long> maxPurchaseId(Long buyerId, Long productId) {
        return JPAExpressions
                .select(purchase.id.max())
                .from(purchase)
                .where(purchase.buyer.id.eq(buyerId),
                        purchase.product.id.eq(productId));
    }

    public boolean isPurchaseAlready(Long buyerId, Long productId) {
        Long purchaseId = jpaQueryFactory
                .select(purchase.id)
                .from(purchase)
                .where(purchase.buyer.id.eq(buyerId),
                        purchase.product.id.eq(productId))
                .fetchFirst();

        return Objects.nonNull(purchaseId);
    }

    public Set<TransactDetail> retrieveProductTransactDetails(Product product) {
        List<TransactDetail> transactDetails = jpaQueryFactory
                .selectDistinct(purchase.purchaseDetail)
                .from(purchase)
                .where(purchase.id.in(lastBuyerPurchaseId(product)))
                .fetch();

        return EnumSet.copyOf(transactDetails);
    }

    private static JPQLQuery<Long> lastBuyerPurchaseId(Product product) {
        return JPAExpressions
                .select(purchase.id.max())
                .from(purchase)
                .groupBy(purchase.buyer)
                .where(purchase.product.eq(product));
    }
}

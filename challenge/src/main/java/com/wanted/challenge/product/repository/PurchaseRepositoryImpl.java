package com.wanted.challenge.product.repository;

import static com.wanted.challenge.product.entity.QPurchase.purchase;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.challenge.product.model.PurchaseDetail;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PurchaseRepositoryImpl implements PurchaseRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public PurchaseDetail retrieveLastPurchaseDetail(Long buyerId, Long productId) {
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
}

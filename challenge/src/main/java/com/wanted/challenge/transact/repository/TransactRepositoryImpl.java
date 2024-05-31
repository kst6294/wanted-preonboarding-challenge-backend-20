package com.wanted.challenge.transact.repository;

import static com.wanted.challenge.transact.entity.QTransact.transact;
import static com.wanted.challenge.transact.entity.QTransactLog.transactLog;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.transact.model.TransactState;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransactRepositoryImpl implements TransactRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public List<TransactState> retrieveAllTransactState(Long transactId) {
        return jpaQueryFactory
                .select(transactLog.transactState)
                .from(transact)

                .where(transact.id.eq(transactId))

                .fetch();
    }

    public List<TransactState> retrieveAllTransactState(Long buyerId, Long productId) {
        return jpaQueryFactory
                .select(transactLog.transactState)
                .from(transact)

                .innerJoin(transactLog)
                .on(transact.eq(transactLog.transact))

                .where(transact.buyer.id.eq(buyerId),
                        transact.product.id.eq(productId))

                .fetch();
    }

    public TransactState retrieveLastTransactState(Long buyerId, Long productId) {
        return jpaQueryFactory
                .select(transactLog.transactState)
                .from(transact)

                .innerJoin(transactLog)
                .on(transact.eq(transactLog.transact))

                .where(transact.buyer.id.eq(buyerId),
                        transact.product.id.eq(productId),
                        transactLog.id.in(lastTransactLogId()))

                .fetchOne();
    }

    private static JPQLQuery<Long> lastTransactLogId() {
        return JPAExpressions
                .select(transactLog.id.max())
                .from(transactLog)
                .groupBy(transactLog.transact);
    }

    public Set<TransactState> retrieveDistinctProductTransactStates(Product product) {
        List<TransactState> transactStates = jpaQueryFactory
                .selectDistinct(transactLog.transactState)
                .from(transact)

                .innerJoin(transactLog)
                .on(transact.eq(transactLog.transact))

                .where(transact.product.eq(product),
                        transactLog.id.in(lastTransactLogId()))

                .fetch();

        return EnumSet.copyOf(transactStates);
    }


}

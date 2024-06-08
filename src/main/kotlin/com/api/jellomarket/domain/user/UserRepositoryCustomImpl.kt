package com.api.jellomarket.domain.user

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(readOnly = true)
class UserRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory,
) : UserRepositoryCustom {
    override fun findByEmail(email: String): User? {
        return queryFactory.select(QUser.user)
            .from(QUser.user)
            .where(QUser.user.email.eq(email))
            .fetchOne()
    }
}
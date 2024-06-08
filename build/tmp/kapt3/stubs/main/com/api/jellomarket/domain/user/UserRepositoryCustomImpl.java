package com.api.jellomarket.domain.user;

@org.springframework.stereotype.Component()
@org.springframework.transaction.annotation.Transactional(readOnly = true)
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/api/jellomarket/domain/user/UserRepositoryCustomImpl;", "Lcom/api/jellomarket/domain/user/UserRepositoryCustom;", "queryFactory", "Lcom/querydsl/jpa/impl/JPAQueryFactory;", "(Lcom/querydsl/jpa/impl/JPAQueryFactory;)V", "findByEmail", "Lcom/api/jellomarket/domain/user/User;", "email", "", "jelloMarket"})
public class UserRepositoryCustomImpl implements com.api.jellomarket.domain.user.UserRepositoryCustom {
    @org.jetbrains.annotations.NotNull()
    private final com.querydsl.jpa.impl.JPAQueryFactory queryFactory = null;
    
    public UserRepositoryCustomImpl(@org.jetbrains.annotations.NotNull()
    com.querydsl.jpa.impl.JPAQueryFactory queryFactory) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public com.api.jellomarket.domain.user.User findByEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email) {
        return null;
    }
}
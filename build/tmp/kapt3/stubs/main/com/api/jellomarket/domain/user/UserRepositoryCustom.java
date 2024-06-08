package com.api.jellomarket.domain.user;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0006"}, d2 = {"Lcom/api/jellomarket/domain/user/UserRepositoryCustom;", "", "findByEmail", "Lcom/api/jellomarket/domain/user/User;", "email", "", "jelloMarket"})
public abstract interface UserRepositoryCustom {
    
    @org.jetbrains.annotations.Nullable()
    public abstract com.api.jellomarket.domain.user.User findByEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email);
}
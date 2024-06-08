package com.api.jellomarket.validation;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\bJ\u0010\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n\u00a8\u0006\f"}, d2 = {"Lcom/api/jellomarket/validation/UserValidation;", "", "()V", "validateSignInRequest", "", "requestDTO", "Lcom/api/jellomarket/dto/user/UserSignInRequestDTO;", "validateSignUpRequest", "Lcom/api/jellomarket/dto/user/UserSignUpRequestDTO;", "validateValidUser", "Lcom/api/jellomarket/domain/user/User;", "user", "jelloMarket"})
public final class UserValidation {
    
    public UserValidation() {
        super();
    }
    
    public final void validateSignUpRequest(@org.jetbrains.annotations.NotNull()
    com.api.jellomarket.dto.user.UserSignUpRequestDTO requestDTO) {
    }
    
    public final void validateSignInRequest(@org.jetbrains.annotations.NotNull()
    com.api.jellomarket.dto.user.UserSignInRequestDTO requestDTO) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.api.jellomarket.domain.user.User validateValidUser(@org.jetbrains.annotations.Nullable()
    com.api.jellomarket.domain.user.User user) {
        return null;
    }
}
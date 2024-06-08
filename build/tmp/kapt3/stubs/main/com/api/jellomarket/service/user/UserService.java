package com.api.jellomarket.service.user;

@org.springframework.stereotype.Service()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\fH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\r"}, d2 = {"Lcom/api/jellomarket/service/user/UserService;", "", "userRepository", "Lcom/api/jellomarket/domain/user/UserRepository;", "(Lcom/api/jellomarket/domain/user/UserRepository;)V", "getUserRepository", "()Lcom/api/jellomarket/domain/user/UserRepository;", "saveUser", "Lcom/api/jellomarket/domain/user/User;", "request", "Lcom/api/jellomarket/dto/user/UserSignUpRequestDTO;", "signInUser", "Lcom/api/jellomarket/dto/user/UserSignInRequestDTO;", "jelloMarket"})
public class UserService {
    @org.jetbrains.annotations.NotNull()
    private final com.api.jellomarket.domain.user.UserRepository userRepository = null;
    
    public UserService(@org.jetbrains.annotations.NotNull()
    com.api.jellomarket.domain.user.UserRepository userRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public com.api.jellomarket.domain.user.UserRepository getUserRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public com.api.jellomarket.domain.user.User saveUser(@org.jetbrains.annotations.NotNull()
    com.api.jellomarket.dto.user.UserSignUpRequestDTO request) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public com.api.jellomarket.domain.user.User signInUser(@org.jetbrains.annotations.NotNull()
    com.api.jellomarket.dto.user.UserSignInRequestDTO request) {
        return null;
    }
}
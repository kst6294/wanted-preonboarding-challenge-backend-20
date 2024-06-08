package com.api.jellomarket.config.auth;

@org.springframework.stereotype.Component()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\u0019\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J.\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\nH\u0016R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/api/jellomarket/config/auth/LoginUserArgumentResolver;", "Lorg/springframework/web/method/support/HandlerMethodArgumentResolver;", "httpSession", "Ljakarta/servlet/http/HttpSession;", "userRepository", "Lcom/api/jellomarket/domain/user/UserRepository;", "(Ljakarta/servlet/http/HttpSession;Lcom/api/jellomarket/domain/user/UserRepository;)V", "resolveArgument", "Lcom/api/jellomarket/domain/user/User;", "parameter", "Lorg/springframework/core/MethodParameter;", "mavContainer", "Lorg/springframework/web/method/support/ModelAndViewContainer;", "webRequest", "Lorg/springframework/web/context/request/NativeWebRequest;", "binderFactory", "Lorg/springframework/web/bind/support/WebDataBinderFactory;", "supportsParameter", "", "jelloMarket"})
@lombok.RequiredArgsConstructor()
public class LoginUserArgumentResolver implements org.springframework.web.method.support.HandlerMethodArgumentResolver {
    @org.jetbrains.annotations.Nullable()
    private final jakarta.servlet.http.HttpSession httpSession = null;
    @org.jetbrains.annotations.NotNull()
    private final com.api.jellomarket.domain.user.UserRepository userRepository = null;
    
    public LoginUserArgumentResolver(@org.jetbrains.annotations.Nullable()
    jakarta.servlet.http.HttpSession httpSession, @org.jetbrains.annotations.NotNull()
    com.api.jellomarket.domain.user.UserRepository userRepository) {
        super();
    }
    
    @java.lang.Override()
    public boolean supportsParameter(@org.jetbrains.annotations.NotNull()
    org.springframework.core.MethodParameter parameter) {
        return false;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public com.api.jellomarket.domain.user.User resolveArgument(@org.jetbrains.annotations.NotNull()
    org.springframework.core.MethodParameter parameter, @org.jetbrains.annotations.Nullable()
    org.springframework.web.method.support.ModelAndViewContainer mavContainer, @org.jetbrains.annotations.NotNull()
    org.springframework.web.context.request.NativeWebRequest webRequest, @org.jetbrains.annotations.Nullable()
    org.springframework.web.bind.support.WebDataBinderFactory binderFactory) {
        return null;
    }
}
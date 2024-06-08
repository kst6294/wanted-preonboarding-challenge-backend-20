package com.api.jellomarket.controller;

@org.springframework.web.bind.annotation.RestController()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\b\u0001\u0010\n\u001a\u00020\u000bH\u0017J\u0018\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\b\u0001\u0010\n\u001a\u00020\rH\u0017R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u000e"}, d2 = {"Lcom/api/jellomarket/controller/UserController;", "", "userService", "Lcom/api/jellomarket/service/user/UserService;", "(Lcom/api/jellomarket/service/user/UserService;)V", "getUserService", "()Lcom/api/jellomarket/service/user/UserService;", "saveUser", "Lorg/springframework/http/ResponseEntity;", "", "request", "Lcom/api/jellomarket/dto/user/UserSignUpRequestDTO;", "signIn", "Lcom/api/jellomarket/dto/user/UserSignInRequestDTO;", "jelloMarket"})
public class UserController {
    @org.jetbrains.annotations.NotNull()
    private final com.api.jellomarket.service.user.UserService userService = null;
    
    public UserController(@org.jetbrains.annotations.NotNull()
    com.api.jellomarket.service.user.UserService userService) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public com.api.jellomarket.service.user.UserService getUserService() {
        return null;
    }
    
    @org.springframework.web.bind.annotation.PostMapping(value = {"/market/v1/user/signUp"})
    @org.jetbrains.annotations.NotNull()
    public org.springframework.http.ResponseEntity<java.lang.String> saveUser(@org.springframework.validation.annotation.Validated()
    @org.springframework.web.bind.annotation.RequestBody()
    @org.jetbrains.annotations.NotNull()
    com.api.jellomarket.dto.user.UserSignUpRequestDTO request) {
        return null;
    }
    
    @org.springframework.web.bind.annotation.PostMapping(value = {"/market/v1/user/signIn"})
    @org.jetbrains.annotations.NotNull()
    public org.springframework.http.ResponseEntity<java.lang.String> signIn(@org.springframework.validation.annotation.Validated()
    @org.springframework.web.bind.annotation.RequestBody()
    @org.jetbrains.annotations.NotNull()
    com.api.jellomarket.dto.user.UserSignInRequestDTO request) {
        return null;
    }
}
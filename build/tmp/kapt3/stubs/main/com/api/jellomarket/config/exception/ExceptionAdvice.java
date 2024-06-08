package com.api.jellomarket.config.exception;

@org.springframework.web.bind.annotation.RestControllerAdvice()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0017J\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\n\u0010\u0006\u001a\u00060\bj\u0002`\tH\u0017\u00a8\u0006\n"}, d2 = {"Lcom/api/jellomarket/config/exception/ExceptionAdvice;", "", "()V", "handleException", "Lorg/springframework/http/ResponseEntity;", "Lcom/api/jellomarket/config/ResponseCustom$Error;", "e", "Lcom/api/jellomarket/exception/BusinessException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "jelloMarket"})
public class ExceptionAdvice {
    
    public ExceptionAdvice() {
        super();
    }
    
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {com.api.jellomarket.exception.BusinessException.class})
    @org.jetbrains.annotations.NotNull()
    public org.springframework.http.ResponseEntity<com.api.jellomarket.config.ResponseCustom.Error> handleException(@org.jetbrains.annotations.NotNull()
    com.api.jellomarket.exception.BusinessException e) {
        return null;
    }
    
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {java.lang.Exception.class})
    @org.jetbrains.annotations.NotNull()
    public org.springframework.http.ResponseEntity<com.api.jellomarket.config.ResponseCustom.Error> handleException(@org.jetbrains.annotations.NotNull()
    java.lang.Exception e) {
        return null;
    }
}
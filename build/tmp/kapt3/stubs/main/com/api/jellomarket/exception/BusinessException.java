package com.api.jellomarket.exception;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u0000 \f2\u00060\u0001j\u0002`\u0002:\u0001\fB\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\r"}, d2 = {"Lcom/api/jellomarket/exception/BusinessException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "errorCode", "Lcom/api/jellomarket/enums/error/ErrorCodeCustom;", "errorMessage", "", "(Lcom/api/jellomarket/enums/error/ErrorCodeCustom;Ljava/lang/String;)V", "getErrorCode", "()Lcom/api/jellomarket/enums/error/ErrorCodeCustom;", "getErrorMessage", "()Ljava/lang/String;", "Companion", "jelloMarket"})
public final class BusinessException extends java.lang.RuntimeException {
    @org.jetbrains.annotations.NotNull()
    private final com.api.jellomarket.enums.error.ErrorCodeCustom errorCode = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.api.jellomarket.exception.BusinessException.Companion Companion = null;
    
    public BusinessException(@org.jetbrains.annotations.NotNull()
    com.api.jellomarket.enums.error.ErrorCodeCustom errorCode, @org.jetbrains.annotations.NotNull()
    java.lang.String errorMessage) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.api.jellomarket.enums.error.ErrorCodeCustom getErrorCode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u00a8\u0006\t"}, d2 = {"Lcom/api/jellomarket/exception/BusinessException$Companion;", "", "()V", "of", "Lcom/api/jellomarket/exception/BusinessException;", "errorCodeCustom", "Lcom/api/jellomarket/enums/error/ErrorCodeCustom;", "errorMessage", "", "jelloMarket"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.api.jellomarket.exception.BusinessException of(@org.jetbrains.annotations.NotNull()
        com.api.jellomarket.enums.error.ErrorCodeCustom errorCodeCustom) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.api.jellomarket.exception.BusinessException of(@org.jetbrains.annotations.NotNull()
        com.api.jellomarket.enums.error.ErrorCodeCustom errorCodeCustom, @org.jetbrains.annotations.NotNull()
        java.lang.String errorMessage) {
            return null;
        }
    }
}
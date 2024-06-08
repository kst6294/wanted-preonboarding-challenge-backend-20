package com.api.jellomarket.config.path;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"PRODUCT", "", "PRODUCT_DETAIL", "PRODUCT_LIST", "PRODUCT_PURCHASE", "ROOT", "USER_SIGN_IN", "USER_SIGN_UP", "VER1", "jelloMarket"})
public final class PathKt {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ROOT = "/market";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String VER1 = "/v1";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String USER_SIGN_UP = "/market/v1/user/signUp";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String USER_SIGN_IN = "/market/v1/user/signIn";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PRODUCT = "/market/v1/product";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PRODUCT_LIST = "/market/v1/product/list";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PRODUCT_DETAIL = "/market/v1/product/{productId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PRODUCT_PURCHASE = "/market/v1/product/purchase/{productId}";
}
package com.api.jellomarket.controller;

@org.springframework.web.bind.annotation.RestController()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0017J\u000e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0017J&\u0010\u0012\u001a\u00020\u00132\b\b\u0001\u0010\u0014\u001a\u00020\u00152\b\b\u0001\u0010\r\u001a\u00020\u000e2\b\b\u0001\u0010\u0016\u001a\u00020\u0017H\u0017J\u001c\u0010\u0018\u001a\u00020\u00192\b\b\u0001\u0010\u0014\u001a\u00020\u00152\b\b\u0001\u0010\u0016\u001a\u00020\u001aH\u0017R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u001b"}, d2 = {"Lcom/api/jellomarket/controller/ProductController;", "", "productService", "Lcom/api/jellomarket/service/product/ProductService;", "productPurchaseService", "Lcom/api/jellomarket/service/product/ProductPurchaseService;", "(Lcom/api/jellomarket/service/product/ProductService;Lcom/api/jellomarket/service/product/ProductPurchaseService;)V", "getProductPurchaseService", "()Lcom/api/jellomarket/service/product/ProductPurchaseService;", "getProductService", "()Lcom/api/jellomarket/service/product/ProductService;", "getProductDetail", "Lcom/api/jellomarket/dto/product/ProductDetailDTO;", "productId", "", "getProductList", "", "Lcom/api/jellomarket/dto/product/ProductListDTO;", "purchaseProduct", "", "user", "Lcom/api/jellomarket/domain/user/User;", "request", "Lcom/api/jellomarket/dto/product/ProductPurchaseRequestDTO;", "saveProduct", "", "Lcom/api/jellomarket/dto/product/ProductCreateRequestDTO;", "jelloMarket"})
public class ProductController {
    @org.jetbrains.annotations.NotNull()
    private final com.api.jellomarket.service.product.ProductService productService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.api.jellomarket.service.product.ProductPurchaseService productPurchaseService = null;
    
    public ProductController(@org.jetbrains.annotations.NotNull()
    com.api.jellomarket.service.product.ProductService productService, @org.jetbrains.annotations.NotNull()
    com.api.jellomarket.service.product.ProductPurchaseService productPurchaseService) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public com.api.jellomarket.service.product.ProductService getProductService() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public com.api.jellomarket.service.product.ProductPurchaseService getProductPurchaseService() {
        return null;
    }
    
    @org.springframework.web.bind.annotation.GetMapping(value = {"/market/v1/product/list"})
    @org.jetbrains.annotations.NotNull()
    public java.util.List<com.api.jellomarket.dto.product.ProductListDTO> getProductList() {
        return null;
    }
    
    @org.springframework.web.bind.annotation.GetMapping(value = {"/market/v1/product/{productId}"})
    @org.jetbrains.annotations.NotNull()
    public com.api.jellomarket.dto.product.ProductDetailDTO getProductDetail(long productId) {
        return null;
    }
    
    @org.springframework.web.bind.annotation.PostMapping(value = {"/market/v1/product"})
    @org.jetbrains.annotations.NotNull()
    public java.lang.String saveProduct(@com.api.jellomarket.config.auth.LoginUser()
    @org.jetbrains.annotations.NotNull()
    com.api.jellomarket.domain.user.User user, @org.springframework.web.bind.annotation.RequestBody()
    @org.jetbrains.annotations.NotNull()
    com.api.jellomarket.dto.product.ProductCreateRequestDTO request) {
        return null;
    }
    
    @org.springframework.web.bind.annotation.PostMapping(value = {"/market/v1/product/purchase/{productId}"})
    public boolean purchaseProduct(@com.api.jellomarket.config.auth.LoginUser()
    @org.jetbrains.annotations.NotNull()
    com.api.jellomarket.domain.user.User user, @org.springframework.web.bind.annotation.PathVariable()
    long productId, @org.springframework.web.bind.annotation.RequestBody()
    @org.jetbrains.annotations.NotNull()
    com.api.jellomarket.dto.product.ProductPurchaseRequestDTO request) {
        return false;
    }
}
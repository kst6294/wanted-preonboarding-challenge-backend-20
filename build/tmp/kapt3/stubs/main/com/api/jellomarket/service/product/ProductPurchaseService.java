package com.api.jellomarket.service.product;

@org.springframework.stereotype.Service()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0017R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0013"}, d2 = {"Lcom/api/jellomarket/service/product/ProductPurchaseService;", "", "productRepository", "Lcom/api/jellomarket/domain/product/ProductRepository;", "orderRepository", "Lcom/api/jellomarket/domain/order/OrderRepository;", "(Lcom/api/jellomarket/domain/product/ProductRepository;Lcom/api/jellomarket/domain/order/OrderRepository;)V", "getOrderRepository", "()Lcom/api/jellomarket/domain/order/OrderRepository;", "getProductRepository", "()Lcom/api/jellomarket/domain/product/ProductRepository;", "purchaseProduct", "Lcom/api/jellomarket/domain/order/Order;", "user", "Lcom/api/jellomarket/domain/user/User;", "productId", "", "productPurchaseRequestDTO", "Lcom/api/jellomarket/dto/product/ProductPurchaseRequestDTO;", "jelloMarket"})
public class ProductPurchaseService {
    @org.jetbrains.annotations.NotNull()
    private final com.api.jellomarket.domain.product.ProductRepository productRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.api.jellomarket.domain.order.OrderRepository orderRepository = null;
    
    public ProductPurchaseService(@org.jetbrains.annotations.NotNull()
    com.api.jellomarket.domain.product.ProductRepository productRepository, @org.jetbrains.annotations.NotNull()
    com.api.jellomarket.domain.order.OrderRepository orderRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public com.api.jellomarket.domain.product.ProductRepository getProductRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public com.api.jellomarket.domain.order.OrderRepository getOrderRepository() {
        return null;
    }
    
    @jakarta.transaction.Transactional()
    @org.jetbrains.annotations.NotNull()
    public com.api.jellomarket.domain.order.Order purchaseProduct(@org.jetbrains.annotations.NotNull()
    com.api.jellomarket.domain.user.User user, long productId, @org.jetbrains.annotations.NotNull()
    com.api.jellomarket.dto.product.ProductPurchaseRequestDTO productPurchaseRequestDTO) {
        return null;
    }
}
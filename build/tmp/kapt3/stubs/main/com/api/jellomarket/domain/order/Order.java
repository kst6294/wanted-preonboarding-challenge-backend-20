package com.api.jellomarket.domain.order;

@jakarta.persistence.Entity()
@jakarta.persistence.Table(name = "jello_orders")
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0011\b\u0007\u0018\u00002\u00020\u0001BO\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\u0002\u0010\u0010R\u0016\u0010\r\u001a\u00020\u000e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u000e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0012R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0014\u00a8\u0006\u001f"}, d2 = {"Lcom/api/jellomarket/domain/order/Order;", "", "orderId", "", "userId", "productId", "quantity", "", "price", "status", "Lcom/api/jellomarket/enums/order/OrderStatus;", "totalAmount", "", "createdAt", "", "updatedAt", "(JJJIILcom/api/jellomarket/enums/order/OrderStatus;FLjava/lang/String;Ljava/lang/String;)V", "getCreatedAt", "()Ljava/lang/String;", "getOrderId", "()J", "getPrice", "()I", "getProductId", "getQuantity", "getStatus", "()Lcom/api/jellomarket/enums/order/OrderStatus;", "getTotalAmount", "()F", "getUpdatedAt", "getUserId", "jelloMarket"})
public final class Order {
    @jakarta.persistence.Id()
    @jakarta.persistence.Column(name = "order_id")
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private final long orderId = 0L;
    @jakarta.persistence.Column(name = "user_id")
    private final long userId = 0L;
    @jakarta.persistence.Column(name = "product_id")
    private final long productId = 0L;
    private final int quantity = 0;
    private final int price = 0;
    @org.jetbrains.annotations.NotNull()
    private final com.api.jellomarket.enums.order.OrderStatus status = null;
    private final float totalAmount = 0.0F;
    @jakarta.persistence.Column(name = "created_at")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String createdAt = null;
    @jakarta.persistence.Column(name = "updated_at")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String updatedAt = null;
    
    public Order(long orderId, long userId, long productId, int quantity, int price, @org.jetbrains.annotations.NotNull()
    com.api.jellomarket.enums.order.OrderStatus status, float totalAmount, @org.jetbrains.annotations.NotNull()
    java.lang.String createdAt, @org.jetbrains.annotations.Nullable()
    java.lang.String updatedAt) {
        super();
    }
    
    public final long getOrderId() {
        return 0L;
    }
    
    public final long getUserId() {
        return 0L;
    }
    
    public final long getProductId() {
        return 0L;
    }
    
    public final int getQuantity() {
        return 0;
    }
    
    public final int getPrice() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.api.jellomarket.enums.order.OrderStatus getStatus() {
        return null;
    }
    
    public final float getTotalAmount() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCreatedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getUpdatedAt() {
        return null;
    }
}
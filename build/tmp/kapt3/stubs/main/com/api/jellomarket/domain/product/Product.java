package com.api.jellomarket.domain.product;

@jakarta.persistence.Entity()
@jakarta.persistence.Table(name = "jello_product")
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b(\b\u0007\u0018\u00002\u00020\u0001By\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0011R \u0010\u000e\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R \u0010\u0010\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0013\"\u0004\b\u0017\u0010\u0015R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0013\"\u0004\b\u0019\u0010\u0015R \u0010\f\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0013\"\u0004\b\u001b\u0010\u0015R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0013\"\u0004\b\u001d\u0010\u0015R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\"\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0010\n\u0002\u0010&\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\"\u0010\r\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0010\n\u0002\u0010&\u001a\u0004\b\'\u0010#\"\u0004\b(\u0010%R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001a\u0010\n\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u001f\"\u0004\b.\u0010!R \u0010\u000f\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u0013\"\u0004\b0\u0010\u0015\u00a8\u00061"}, d2 = {"Lcom/api/jellomarket/domain/product/Product;", "", "productId", "", "name", "", "price", "", "state", "Lcom/api/jellomarket/enums/product/ProductState;", "stock", "description", "imageUrl", "sellerId", "createdAt", "updatedAt", "deletedAt", "(Ljava/lang/Long;Ljava/lang/String;ILcom/api/jellomarket/enums/product/ProductState;ILjava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCreatedAt", "()Ljava/lang/String;", "setCreatedAt", "(Ljava/lang/String;)V", "getDeletedAt", "setDeletedAt", "getDescription", "setDescription", "getImageUrl", "setImageUrl", "getName", "setName", "getPrice", "()I", "setPrice", "(I)V", "getProductId", "()Ljava/lang/Long;", "setProductId", "(Ljava/lang/Long;)V", "Ljava/lang/Long;", "getSellerId", "setSellerId", "getState", "()Lcom/api/jellomarket/enums/product/ProductState;", "setState", "(Lcom/api/jellomarket/enums/product/ProductState;)V", "getStock", "setStock", "getUpdatedAt", "setUpdatedAt", "jelloMarket"})
public final class Product {
    @jakarta.persistence.Id()
    @jakarta.persistence.Column(name = "product_id")
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @org.jetbrains.annotations.Nullable()
    private java.lang.Long productId;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String name;
    private int price;
    @org.jetbrains.annotations.NotNull()
    private com.api.jellomarket.enums.product.ProductState state;
    private int stock;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String description;
    @jakarta.persistence.Column(name = "image_url")
    @org.jetbrains.annotations.Nullable()
    private java.lang.String imageUrl;
    @jakarta.persistence.Column(name = "seller_id")
    @org.jetbrains.annotations.Nullable()
    private java.lang.Long sellerId;
    @jakarta.persistence.Column(name = "created_at")
    @org.jetbrains.annotations.Nullable()
    private java.lang.String createdAt;
    @jakarta.persistence.Column(name = "updated_at")
    @org.jetbrains.annotations.Nullable()
    private java.lang.String updatedAt;
    @jakarta.persistence.Column(name = "deleted_at")
    @org.jetbrains.annotations.Nullable()
    private java.lang.String deletedAt;
    
    public Product(@org.jetbrains.annotations.Nullable()
    java.lang.Long productId, @org.jetbrains.annotations.NotNull()
    java.lang.String name, int price, @org.jetbrains.annotations.NotNull()
    com.api.jellomarket.enums.product.ProductState state, int stock, @org.jetbrains.annotations.Nullable()
    java.lang.String description, @org.jetbrains.annotations.Nullable()
    java.lang.String imageUrl, @org.jetbrains.annotations.Nullable()
    java.lang.Long sellerId, @org.jetbrains.annotations.Nullable()
    java.lang.String createdAt, @org.jetbrains.annotations.Nullable()
    java.lang.String updatedAt, @org.jetbrains.annotations.Nullable()
    java.lang.String deletedAt) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getProductId() {
        return null;
    }
    
    public final void setProductId(@org.jetbrains.annotations.Nullable()
    java.lang.Long p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    public final void setName(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final int getPrice() {
        return 0;
    }
    
    public final void setPrice(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.api.jellomarket.enums.product.ProductState getState() {
        return null;
    }
    
    public final void setState(@org.jetbrains.annotations.NotNull()
    com.api.jellomarket.enums.product.ProductState p0) {
    }
    
    public final int getStock() {
        return 0;
    }
    
    public final void setStock(int p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDescription() {
        return null;
    }
    
    public final void setDescription(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getImageUrl() {
        return null;
    }
    
    public final void setImageUrl(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getSellerId() {
        return null;
    }
    
    public final void setSellerId(@org.jetbrains.annotations.Nullable()
    java.lang.Long p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCreatedAt() {
        return null;
    }
    
    public final void setCreatedAt(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getUpdatedAt() {
        return null;
    }
    
    public final void setUpdatedAt(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDeletedAt() {
        return null;
    }
    
    public final void setDeletedAt(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
}
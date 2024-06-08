package com.api.jellomarket.service.product;

@org.springframework.stereotype.Service()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016J\u001a\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0014"}, d2 = {"Lcom/api/jellomarket/service/product/ProductService;", "", "productRepository", "Lcom/api/jellomarket/domain/product/ProductRepository;", "(Lcom/api/jellomarket/domain/product/ProductRepository;)V", "getProductRepository", "()Lcom/api/jellomarket/domain/product/ProductRepository;", "getProductDetail", "Lcom/api/jellomarket/dto/product/ProductDetailDTO;", "productId", "", "getProductList", "", "Lcom/api/jellomarket/dto/product/ProductListDTO;", "saveProduct", "", "user", "Lcom/api/jellomarket/domain/user/User;", "request", "Lcom/api/jellomarket/dto/product/ProductCreateRequestDTO;", "jelloMarket"})
public class ProductService {
    @org.jetbrains.annotations.NotNull()
    private final com.api.jellomarket.domain.product.ProductRepository productRepository = null;
    
    public ProductService(@org.jetbrains.annotations.NotNull()
    com.api.jellomarket.domain.product.ProductRepository productRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public com.api.jellomarket.domain.product.ProductRepository getProductRepository() {
        return null;
    }
    
    /**
     * 상품 목록 조회
     * @return List<Product>
     */
    @org.jetbrains.annotations.NotNull()
    public java.util.List<com.api.jellomarket.dto.product.ProductListDTO> getProductList() {
        return null;
    }
    
    /**
     * 상품 상세 조회
     * @param productId
     * @return Product
     */
    @org.jetbrains.annotations.NotNull()
    public com.api.jellomarket.dto.product.ProductDetailDTO getProductDetail(long productId) {
        return null;
    }
    
    /**
     * 상품 등록
     * @param request
     * @return String
     */
    @org.jetbrains.annotations.NotNull()
    public java.lang.String saveProduct(@org.jetbrains.annotations.Nullable()
    com.api.jellomarket.domain.user.User user, @org.jetbrains.annotations.NotNull()
    com.api.jellomarket.dto.product.ProductCreateRequestDTO request) {
        return null;
    }
}
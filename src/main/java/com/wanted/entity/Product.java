package com.wanted.entity;

import com.wanted.dto.ProductDto;
import com.wanted.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;
    private Long price;

    @Column(name = "stock_quantity")
    private Long stockQuantity;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    /* 비즈니스 로직 */
    public void removeStock(Long quantity){
        long result = this.stockQuantity - quantity;

        if(result < 0){
            throw new NotEnoughStockException("재고가 부족합니다.");
        }
        this.stockQuantity = result;
    }

    public void addStock(Long quantity){
        this.stockQuantity += quantity;
    }

    public void addStatus(){
        this.status = ProductStatus.판매중;
    }

    /* DTO 변환 메서드 */
    public ProductDto toDto(){
        return new ProductDto(id, name, price, stockQuantity, status.name());
    }

    /* 생성 메서드 */
    public static Product createProduct(String name, Long price, Long stockQuantity, Member member){
        Product product = new Product(name, price, stockQuantity , member);
        product.addStatus();

        return product;
    }


    /* 생성자 */
    public Product(String name, Long price, Long stockQuantity, Member member) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.member = member;
    }

    public Product(ProductStatus status) {
        this.status = status;
    }
}

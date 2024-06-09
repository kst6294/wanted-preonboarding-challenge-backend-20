package org.example.preonboarding.product.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.preonboarding.common.entity.BaseEntity;
import org.example.preonboarding.member.model.entity.Member;
import org.example.preonboarding.product.model.enums.ProductSellingStatus;
import org.example.preonboarding.product.model.enums.ProductType;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "products")
@Comment("제품")
@Entity
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Comment("제품 번호")
    private String productNumber;

    @Enumerated(EnumType.STRING)
    @Comment("제품 타입")
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    @Comment("제품 판매 상태")
    private ProductSellingStatus productSellingStatus;

    @Column(length = 1000)
    @Comment("제품 설명")
    private String description;

    @Column(nullable = false)
    @Comment("제품명")
    private String name;

    @Column(nullable = false)
    @Comment("제품 가격")
    private int price;

    @Column
    @Comment("제품 삭제일시")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selling_user_id")
    private Member seller;

    @Builder
    private Product(String productNumber, ProductType productType, ProductSellingStatus productSellingStatus, String description, String name, int price, LocalDateTime deletedAt, Member seller) {
        this.productNumber = productNumber;
        this.productType = productType;
        this.productSellingStatus = productSellingStatus;
        this.description = description;
        this.name = name;
        this.price = price;
        this.deletedAt = deletedAt;
        this.seller = seller;
    }

    public void inSelling() {
        this.productSellingStatus = ProductSellingStatus.SELLING;
    }

    public void soldOut() {
        this.productSellingStatus = ProductSellingStatus.SOLD_OUT;
    }

    public void reserved() {
        this.productSellingStatus = ProductSellingStatus.RESERVED;
    }

}

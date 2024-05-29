package com.wanted.preonboarding.backend20.domain.product.domain;

import com.wanted.preonboarding.backend20.domain.member.domain.Member;
import com.wanted.preonboarding.backend20.domain.product.dto.ProductRequestDto;
import com.wanted.preonboarding.backend20.domain.product.enums.ProductStatus;
import com.wanted.preonboarding.backend20.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Min(1)
    private int price;

    @NotNull
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ProductStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member seller;

    @NotNull
    private int totalQuantity;

    @Builder
    public Product(ProductRequestDto dto, Member seller) {
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.description = dto.getDescription();
        this.status = ProductStatus.SALE;
        this.seller = seller;
        this.totalQuantity = dto.getTotalQuantity();
    }

    public void updateProduct(ProductRequestDto dto) {
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.totalQuantity = dto.getTotalQuantity();
    }

    public void productAllReserved() {
        this.status = ProductStatus.RESERVED;
    }

    public void productSoldOut() {
        this.status = ProductStatus.SOLD;
    }

    public void reduceProductQuantity() {
        this.totalQuantity -= 1;
    }
}

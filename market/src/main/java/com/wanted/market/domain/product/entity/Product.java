package com.wanted.market.domain.product.entity;

import com.wanted.market.domain.member.entity.Member;
import com.wanted.market.global.common.code.ProductStatusCode;
import com.wanted.market.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Table(name = "product")
@Entity
@Getter
@Builder
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "product_number")
    private long productNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_number")
    private Member seller;

    private String name;

    private long price;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProductStatusCode status;

    public Product() {
    }

    public Product(Long productNo, Member seller, String name, long price, String description, ProductStatusCode status) {
        this.productNo = productNo;
        this.seller = seller;
        this.name = name;
        this.price = price;
        this.description = description;
        this.status = status;
    }

    public void changeStatus(ProductStatusCode status) {
        this.status = status;
    }

    public boolean isAvailable() {
        return this.status.equals(ProductStatusCode.ON_SALE);
    }
}

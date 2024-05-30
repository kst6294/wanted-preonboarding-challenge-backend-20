package com.market.wanted.product.entity;

import com.market.wanted.common.entity.BaseEntity;
import com.market.wanted.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Product extends BaseEntity {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private long price;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member seller;

    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.SALE;


    public void changeStatus(ProductStatus status) {
        this.status = status;
    }

    public Product(String productName, long price, Member buyer) {
        this.productName = productName;
        this.price = price;
        this.seller = buyer;
    }

    public void addSeller(Member seller) {
        this.seller = seller;
    }
}

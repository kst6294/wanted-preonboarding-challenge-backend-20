package com.wanted.market.domain.product.entity;

import com.wanted.market.domain.member.entity.Member;
import com.wanted.market.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Table(name = "product")
@Entity
@Getter
@Builder
@AllArgsConstructor
public class Product extends BaseTimeEntity {

    private final int SOLD_OUT = 0;

    @Id
    @GeneratedValue
    @Column(name = "product_number")
    private long productNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_number")
    private Member seller;

    private String name;

    private long price;

    private int quantity;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProductStatusCode status;

    public Product() {
    }

    public void changeStatus() {

        if (quantity == SOLD_OUT) {
            this.status = ProductStatusCode.COMPLETE;
            return;
        }

        quantity -= 1;

        if (quantity == SOLD_OUT) {
            this.status = ProductStatusCode.RESERVE;
        } else {
            this.status = ProductStatusCode.ON_SALE;
        }
    }

    public boolean isAvailable() {
        return this.status.equals(ProductStatusCode.ON_SALE);
    }
}

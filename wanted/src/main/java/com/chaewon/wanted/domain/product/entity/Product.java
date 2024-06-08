package com.chaewon.wanted.domain.product.entity;

import com.chaewon.wanted.common.BaseEntity;
import com.chaewon.wanted.domain.member.entity.Member;
import com.chaewon.wanted.domain.orders.exception.NotEnoughQuantityException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Product extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "quantity")
    private int quantity;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 판매자

    public void updateProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public void updateProductStatusOnOrderCreation(long orderCount) {
        if (this.quantity == orderCount) {
            this.updateProductStatus(ProductStatus.예약중);
        } else {
            this.updateProductStatus(ProductStatus.판매중);
        }
    }

    public void updateProductStatusOnOrders(boolean allOrdersConfirmed, boolean isQuantityAvailable, boolean hasPending) {
        if (allOrdersConfirmed && !isQuantityAvailable) {
            this.updateProductStatus(ProductStatus.완료);
        } else if (isQuantityAvailable || hasPending) {
            this.updateProductStatus(hasPending ? ProductStatus.예약중 : ProductStatus.판매중);
        }
    }

    public void decreaseQuantity(int amount) {
        if (this.quantity < amount) {
            throw new NotEnoughQuantityException("현재 남은 수량이 없습니다.");
        }
        this.quantity -= amount;
    }

    public void modifyProduct(int price) {
        this.price = price;
    }
}

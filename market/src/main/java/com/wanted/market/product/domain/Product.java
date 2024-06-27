package com.wanted.market.product.domain;


import com.wanted.market.common.entity.BaseEntity;
import com.wanted.market.member.domain.Member;
import com.wanted.market.order.domain.Order;
import com.wanted.market.product.model.ProductStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "product_id"))
public class Product extends BaseEntity {

    @Column(name = "product_name", nullable = false)
    private String name;

    @Min(0)
    @Column(nullable = false)
    private Integer price;

    @Min(0)
    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus productStatus;

    @JoinColumn(name = "seller")
    @ManyToOne
    private Member seller;

    @OneToMany(mappedBy = "product")
    private List<Order> orders = new ArrayList<>();

    public void order(Integer quantity) {
        this.quantity -= quantity;
    }

    public void modifyStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    // 필드 업데이트 메서드
    public void updateName(String name) {
        this.name = name;
    }

    public void updatePrice(Integer price) {
        this.price = price;
    }

    public void updateQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

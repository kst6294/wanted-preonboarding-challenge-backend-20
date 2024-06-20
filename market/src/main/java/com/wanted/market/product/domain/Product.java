package com.wanted.market.product.domain;


import com.wanted.market.member.domain.Member;
import com.wanted.market.order.domain.Order;
import com.wanted.market.product.model.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer id;

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
}

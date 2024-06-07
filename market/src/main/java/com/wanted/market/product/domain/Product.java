package com.wanted.market.product.domain;


import com.wanted.market.member.domain.Member;
import com.wanted.market.order.domain.Order;
import com.wanted.market.product.model.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Min;
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

    @Column(nullable = false)
    private String name;

    @Column
    @Min(0)
    private Integer price;

    @Column
    @Min(0)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @OneToMany
    private List<Order> orders;

    public void order() {
        this.quantity--;
    }

}

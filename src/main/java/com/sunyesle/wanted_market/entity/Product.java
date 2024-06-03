package com.sunyesle.wanted_market.entity;

import com.sunyesle.wanted_market.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private String name;

    private Integer price;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    public void setStatus(ProductStatus status) {
        this.status = status;
    }
}

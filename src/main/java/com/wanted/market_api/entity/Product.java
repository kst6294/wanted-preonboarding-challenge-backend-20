package com.wanted.market_api.entity;

import com.wanted.market_api.constant.ProductStatus;
import com.wanted.market_api.dto.request.product.ProductCreateRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int price;
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}

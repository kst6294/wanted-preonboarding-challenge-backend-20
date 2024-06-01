package com.chaewon.wanted.domain.product.entity;

import com.chaewon.wanted.common.BaseEntity;
import com.chaewon.wanted.domain.member.entity.Member;
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

    @Column(name = "quantity") // 2차 요구사항 때 구현
    private int quantity;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}

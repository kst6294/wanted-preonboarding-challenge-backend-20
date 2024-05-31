package com.wanted.challenge.domain.item.entity;

import com.wanted.challenge.domain.member.entity.Member;
import com.wanted.challenge.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private boolean reservation_status;

    @Column(nullable = false)
    private SaleStatus saleStatus;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Item(String name, Long price, boolean reservation_status, SaleStatus saleStatus, Integer quantity, Member member) {
        this.name = name;
        this.price = price;
        this.reservation_status = reservation_status;
        this.saleStatus = saleStatus;
        this.quantity = quantity;
        this.member = member;
    }
}

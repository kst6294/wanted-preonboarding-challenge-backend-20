package com.wanted.challenge.domain.item.entity;

import com.wanted.challenge.domain.entity.BaseTimeEntity;
import com.wanted.challenge.domain.member.entity.Member;
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

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private SaleStatus saleStatus;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public Item(String name, Long price, SaleStatus saleStatus, Integer quantity, Member member) {
        this.name = name;
        this.price = price;
        this.saleStatus = saleStatus;
        this.quantity = quantity;
        this.member = member;
    }

    // 재고 감소
    public void decreaseQuantity(){
        this.quantity -= 1;
    }
    
    // 재고가 0인경우 상태 변경(예약중)
    public void changeSaleStatusReserved(){
        this.saleStatus = SaleStatus.RESERVED;
    }

    // 재고가 0이고 구매확정이 끝나면(판매완료)
    public void changeSaleStatusSoldOut(){
        this.saleStatus = SaleStatus.SOLD_OUT;
    }
}

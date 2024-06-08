package com.wanted.challenge.domain.transactionhistory.entity;

import com.wanted.challenge.domain.entity.BaseTimeEntity;
import com.wanted.challenge.domain.item.entity.Item;
import com.wanted.challenge.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TransactionHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean saleConfirmStatus;

    @Column(nullable = false)
    private boolean purchaseConfirmStatus;

    @Column(nullable = false)
    private Long purchasePrice;

    private LocalDateTime purchaseConfirmDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public TransactionHistory(boolean saleConfirmStatus, boolean purchaseConfirmStatus, Long purchasePrice, Item item, Member member) {
        this.saleConfirmStatus = saleConfirmStatus;
        this.purchaseConfirmStatus = purchaseConfirmStatus;
        this.purchasePrice = purchasePrice;
        this.item = item;
        this.member = member;
    }

    // 판매 승인
    public void confirmSale(){
        this.saleConfirmStatus = true;
    }

    // 구매 확인
    public void confirmPurchase(){
        this.purchaseConfirmStatus = true;
        this.purchaseConfirmDate = LocalDateTime.now();
    }
}

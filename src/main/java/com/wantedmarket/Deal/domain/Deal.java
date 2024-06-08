package com.wantedmarket.Deal.domain;

import com.wantedmarket.Deal.type.DealStatus;
import com.wantedmarket.Item.domain.Item;
import com.wantedmarket.global.domain.BaseEntity;
import com.wantedmarket.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.wantedmarket.Deal.type.DealStatus.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Deal extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Member buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Member seller;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DealStatus dealStatus;

    public void approve() {
        this.dealStatus = APPROVED;
    }
}
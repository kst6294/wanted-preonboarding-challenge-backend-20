package com.wantedmarket.Item.domain;

import com.wantedmarket.Item.type.ItemStatus;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.wantedmarket.Item.type.ItemStatus.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member seller;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    public void reserve() {
        this.itemStatus = RESERVED;
    }

    public void complete() {
        this.itemStatus = COMPLETED;
    }
}
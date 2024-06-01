package com.market.wanted.item;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Long id;
    private String name;
    private Long price;
    private Long saleCount;
    private Long sellerId;
    private Long reservationCount = 0l;

    @Builder
    private Item(String name, Long price, Long saleCount, Long sellerId){
        this.name = name;
        this.price = price;
        this.saleCount = saleCount;
        this.sellerId = sellerId;
    }

    public void removeCount(){
        Long remainCount = saleCount - 1;
        if(remainCount < 0) throw new IllegalArgumentException("제품 수량이 부족합니다.");
        saleCount = remainCount;
    }

    public void removeReservationCount(){
        Long remainCount = reservationCount - 1;
        if(remainCount < 0) throw new IllegalArgumentException("제품 수량이 부족합니다.");
        reservationCount = remainCount;
    }

    public void addReservationCount(){
        reservationCount += 1;
    }
}

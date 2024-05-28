package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Buy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sellId; //판매자Id
    private Long purchaseId; //구매자ID

    private int price; // 구매할 때 가격


    @Enumerated(EnumType.STRING)
    private ItemState itemState; // 예약상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


    public Buy(Long sellId, Long purchaseId, int price, ItemState itemState, Item item ){
        this.sellId = sellId;
        this.purchaseId = purchaseId;
        this.price = price;
        this.itemState = itemState;
        this.item = item;
    }

    public void changeItemState(ItemState itemState){
        this.itemState = itemState;
    }

}

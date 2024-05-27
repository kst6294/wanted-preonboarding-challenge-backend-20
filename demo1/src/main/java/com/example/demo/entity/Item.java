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
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;

    @Enumerated(EnumType.STRING)
    private ItemState itemState; // 예약상태

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Member member;

    public Item(String name, int price, ItemState itemState, Member member){
        this.name = name;
        this.price = price;
        this.itemState = itemState;
        this.member = member;
    }

    public void changeItemState(ItemState itemState){
        this.itemState = itemState;
    }
}

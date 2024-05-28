package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    private int quantity;

    @Enumerated(EnumType.STRING)
    private ItemState itemState; // 예약상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Member member;

    @OneToMany(mappedBy = "item")
    private List<Buy> buys= new ArrayList<>();

    public Item(String name, int price, int quantity, ItemState itemState, Member member){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.itemState = itemState;
        this.member = member;
    }

    /*
     * 아이템 판매 상태 변경
     */
    public void changeItemState(ItemState itemState){
        this.itemState = itemState;
    }

    public void reduceItem(Integer quantity) {
        if(this.quantity - quantity < 0){
            throw new RuntimeException("재고 부족");
        }
        this.quantity = this.quantity - quantity;
    }
}

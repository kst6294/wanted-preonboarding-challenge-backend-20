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

    private Long sellId;
    private Long purchaseId;


    @Enumerated(EnumType.STRING)
    private ItemState itemState; // 예약상태

    public Buy(Long sellId, Long purchaseId, ItemState itemState){
        this.sellId = sellId;
        this.purchaseId = purchaseId;
        this.itemState = itemState;
    }

}

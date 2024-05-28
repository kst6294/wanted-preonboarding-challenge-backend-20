package com.example.demo.dto.response;

import com.example.demo.entity.Buy;
import com.example.demo.entity.Item;
import com.example.demo.entity.ItemState;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public record ItemHistory (
         Long id,
         Long sellId, //판매자Id
         Long purchaseId, //구매자ID
         int price,
         ItemState itemState,// 예약상태
         Long buy_id //아이템 번호


){

    public ItemHistory(Buy buy){
        this(
                buy.getId(),
                buy.getSellId(),
                buy.getPurchaseId(),
                buy.getPrice(),
                buy.getItemState(),
                buy.getItem().getId()
        );
    }

}

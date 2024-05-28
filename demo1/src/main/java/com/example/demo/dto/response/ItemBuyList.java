package com.example.demo.dto.response;

import com.example.demo.entity.Buy;
import com.example.demo.entity.ItemState;

public record ItemBuyList(
         Long id,
         Long sellId, //판매자Id
         Long purchaseId, //구매자ID
         int price,
         ItemState itemState,// 예약상태
         Long buy_id //아이템 번호


){

    public ItemBuyList(Buy buy){
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

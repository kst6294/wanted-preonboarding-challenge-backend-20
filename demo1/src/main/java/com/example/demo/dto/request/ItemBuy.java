package com.example.demo.dto.request;

import com.example.demo.entity.ItemState;
import com.example.demo.entity.Member;

public record ItemBuy(

        Long id, //거래번호
        Long item_id, //아이템 NUM

        ItemState itemState //아이템 상태

) {
}

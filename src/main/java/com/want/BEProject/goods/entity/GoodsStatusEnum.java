package com.want.BEProject.goods.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum GoodsStatusEnum {

    SOLDING("SOLDING"),
    BOOKING("BOOKING"),
    FINISH("FINISH");

    private final String statusType;

}

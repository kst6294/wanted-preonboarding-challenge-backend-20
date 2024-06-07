package com.want.BEProject.goods.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsRegisterRequest {

    private String goodsId;

    private int price;

    private int quantity;


}

package com.want.BEProject.goods.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long sellerId;

    @Column(nullable = false)
    private String goodsName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int orderQuantity;

    @Column(nullable = false)
    private int solledQuantity;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private GoodsStatusEnum status;

    public Goods(long sellerId, String goodsName, int price, int quantity) {
        this.sellerId = sellerId;
        this.goodsName = goodsName;
        this.price = price;
        this.quantity = quantity;
        this.orderQuantity = 0;
        this.solledQuantity = 0;
        this.status = GoodsStatusEnum.BOOKING;
    }

    public String goodsName() {
        return this.getGoodsName();
    }
}

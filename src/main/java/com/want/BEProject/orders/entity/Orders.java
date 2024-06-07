package com.want.BEProject.orders.entity;

import com.want.BEProject.goods.entity.GoodsStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long sellerId;

    @Column(nullable = false)
    private long buyerId;

    @Column(nullable = false)
    private long goodsId;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String goodsName;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private GoodsStatusEnum status;

    @Column(nullable = false)
    private int orderQuantity;

    public Orders(long sellerId, long buyerId, long goodsId, String goodsName,int price, int orderQuantity) {
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.price = price;
        this.status = GoodsStatusEnum.BOOKING;
        this.orderQuantity = orderQuantity;
    }

    public void Solled(){
        this.status = GoodsStatusEnum.FINISH;
    }

    public String goodsName(){
        return this.goodsName;
    }
}

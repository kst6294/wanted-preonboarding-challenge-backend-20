package com.want.BEProject.orders.service;

import com.want.BEProject.goods.entity.Goods;
import com.want.BEProject.goods.entity.GoodsStatusEnum;
import com.want.BEProject.goods.repository.GoodsRepository;
import com.want.BEProject.orders.entity.Orders;
import com.want.BEProject.orders.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final GoodsRepository goodsRepository;

    public void request(long sellerId, long buyerId, long goodsId, String goodsName, int price){
        Goods goods = goodsRepository.findById(goodsId).orElse(null);
        if (goods == null) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }

        if(goods.getOrderQuantity() >= goods.getQuantity()){
            throw new IllegalArgumentException("재고가 부족합니다.");
        }

        goods.setOrderQuantity(goods.getOrderQuantity()+1);
        if(goods.getOrderQuantity() == goods.getQuantity()){
            goods.setStatus(GoodsStatusEnum.BOOKING);
        }



        goodsRepository.save(goods);

        Orders order = new Orders(sellerId, buyerId, goodsId, goodsName, price, 1);
        ordersRepository.save(order);
    }

    public void decide(long ordersId){
        Orders order = ordersRepository.findById(ordersId).orElse(null);
        if(order == null){
            throw new IllegalArgumentException("존재하지 않는 주문입니다.");
        }

        Goods goods = goodsRepository.findById(order.getGoodsId()).orElse(null);
        goods.setSolledQuantity(goods.getSolledQuantity()+1);

        if(goods.getSolledQuantity() == goods.getQuantity()){
            goods.setStatus(GoodsStatusEnum.FINISH);
        }

        order.Solled();
        ordersRepository.save(order);
    }
}

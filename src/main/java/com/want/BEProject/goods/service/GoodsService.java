package com.want.BEProject.goods.service;

import com.want.BEProject.goods.entity.Goods;
import com.want.BEProject.goods.entity.GoodsStatusEnum;
import com.want.BEProject.goods.repository.GoodsRepository;
import com.want.BEProject.orders.entity.Orders;
import com.want.BEProject.orders.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;
    private final OrdersRepository ordersRepository;

    public void registerGoods(long sellerId, String goodsName, int price, int quantity){
        Goods goods = new Goods(sellerId, goodsName, price, quantity);
        goodsRepository.save(goods);
    }


    public ResponseEntity<List<String>> getList(long id) {

        List<Orders> myBuy = ordersRepository.findAllByBuyerId(id);

        List<Goods> mySell = goodsRepository.findAllBySellerId(id);

        List<String> myAll = Stream.concat(
                mySell.stream().filter(goods -> goods.getStatus() == GoodsStatusEnum.BOOKING)
                        .map(Goods::goodsName),
                myBuy.stream().map(Orders::goodsName)
        ).collect(Collectors.toList());

        return ResponseEntity.ok(myAll);

    }
}

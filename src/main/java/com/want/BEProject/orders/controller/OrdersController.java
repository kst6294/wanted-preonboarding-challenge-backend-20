package com.want.BEProject.orders.controller;

import com.want.BEProject.goods.entity.Goods;
import com.want.BEProject.goods.repository.GoodsRepository;
import com.want.BEProject.orders.entity.Orders;
import com.want.BEProject.orders.repository.OrdersRepository;
import com.want.BEProject.orders.service.OrdersService;
import com.want.BEProject.user.entity.Users;
import com.want.BEProject.user.repository.UserRepository;
import com.want.BEProject.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final UserRepository userRepository;
    private final GoodsRepository goodsRepository;
    private final OrdersRepository ordersRepository;
    private final OrdersService ordersService;

    @PostMapping("/request")
    public HttpStatus request(@RequestParam long goodsId, HttpSession session){

        String userId = SessionUtil.getLoginId(session);
        Users buyer = userRepository.findByUserId(userId).orElse(null);
        if (buyer == null) {
            throw new IllegalArgumentException("세션에서 사용자를 불러오지 못했습니다.");
        }

        Goods goods =  goodsRepository.findById(goodsId).orElse(null);
        if (goods == null) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }

        Users seller = userRepository.findById(goods.getSellerId()).orElse(null);
        if (seller == null) {
            throw new IllegalArgumentException("존재하지 않는 판매자입니다.");
        }

        ordersService.request(buyer.getId(), seller.getId(), goods.getId(), goods.getGoodsName(), goods.getPrice());

        return HttpStatus.OK;
    }

    @PostMapping("/decide")
    public HttpStatus decide(@RequestParam long ordersId, HttpSession session){
        String userId = SessionUtil.getLoginId(session);
        Users seller = userRepository.findByUserId(userId).orElse(null);
        if (seller == null) {
            throw new IllegalArgumentException("세션에서 사용자를 불러오지 못했습니다.");
        }

        Orders orders = ordersRepository.findById(ordersId).orElse(null);
        if(seller.getId()!= orders.getSellerId()){
            throw new IllegalArgumentException("판매자가 아닙니다.");
        }

        ordersService.decide(ordersId);

        return HttpStatus.OK;
    }

}

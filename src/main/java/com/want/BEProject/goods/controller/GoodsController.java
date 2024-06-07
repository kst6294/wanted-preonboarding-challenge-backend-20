package com.want.BEProject.goods.controller;

import com.want.BEProject.goods.dto.GoodsRegisterRequest;
import com.want.BEProject.goods.service.GoodsService;
import com.want.BEProject.user.entity.Users;
import com.want.BEProject.user.repository.UserRepository;
import com.want.BEProject.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;
    private final UserRepository userRepository;

    //제품 등록
    @PostMapping("/register")
    public HttpStatus register(@RequestBody GoodsRegisterRequest goodsRegisterRequest, HttpSession session) {

        String userId = SessionUtil.getLoginId(session);
        Users users = userRepository.findByUserId(userId).orElse(null);

        if (users == null) {
            throw new IllegalArgumentException("세션에서 사용자를 불러오지 못했습니다.");
        }

        goodsService.registerGoods(users.getId(),
                goodsRegisterRequest.getGoodsId(),
                goodsRegisterRequest.getPrice(),
                goodsRegisterRequest.getQuantity());

        return HttpStatus.OK;
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> list(HttpSession session){
        String userId = SessionUtil.getLoginId(session);
        Users users = userRepository.findByUserId(userId).orElse(null);

        if (users == null) {
            throw new IllegalArgumentException("세션에서 사용자를 불러오지 못했습니다.");
        }

        return goodsService.getList(users.getId());
    }

}

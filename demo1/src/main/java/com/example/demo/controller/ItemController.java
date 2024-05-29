package com.example.demo.controller;

import com.example.demo.dto.request.ItemBuy;
import com.example.demo.dto.request.ItemSave;
import com.example.demo.entity.Item;
import com.example.demo.entity.ItemState;
import com.example.demo.exception.ItemBuyException;
import com.example.demo.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/api/v1/item/save")
    public ResponseEntity<?> itemSave(Authentication authentication, @RequestBody ItemSave itemSave){

        if(itemService.itemSave(authentication, itemSave)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("실패");
    }

    @PostMapping("/api/v1/item/buy")
    public ResponseEntity<?> itemBuy(Authentication authentication, @RequestBody ItemBuy itemBuy){

        if(itemService.itemBuy(authentication, itemBuy)){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();

    }

    @GetMapping("/api/v1/item/{id}")
    public ResponseEntity<?> item(@PathVariable("id") Long id){
        return ResponseEntity.ok(itemService.findOne(id));
    }

    @GetMapping("/api/v1/item")
    public ResponseEntity<?> itemAll(Pageable pageable){
        return ResponseEntity.ok(itemService.findAll(pageable));
    }

    //당사자간의 모든 거래내역 조회
    @GetMapping("/api/v1/item/history")
    public ResponseEntity<?> itemHistory(Authentication authentication, @RequestBody ItemBuy itemBuy){
        return ResponseEntity.ok(itemService.itemHistory(authentication, itemBuy));
    }

    //구매한 용품 목록 확인
    @GetMapping("/api/v1/item/purchased")
    public ResponseEntity<?> getPurchasedItems(Authentication authentication) {
       return ResponseEntity.ok(itemService.getPurchasedItems(authentication));
    }

    //예약중인 용품 목록 확인
    @GetMapping("/api/v1/item/reserved")
    public ResponseEntity<?> getReservedItems(Authentication authentication) {
        return ResponseEntity.ok(itemService.getReservedItems(authentication));
    }



}

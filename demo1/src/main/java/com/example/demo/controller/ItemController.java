package com.example.demo.controller;

import com.example.demo.dto.request.ItemBuy;
import com.example.demo.dto.request.ItemSave;
import com.example.demo.entity.Item;
import com.example.demo.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/api/v1/item/buy")
    public ResponseEntity<?> itemBuy(Authentication authentication, @RequestBody ItemBuy itemBuy){

        if(itemService.itemBuy(itemBuy)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/api/v1/item/{id}")
    public ResponseEntity<?> item(@PathVariable Long id){
        return ResponseEntity.ok(itemService.findOne(id));
    }

    @GetMapping("/api/v1/item")
    public ResponseEntity<?> itemAll(Pageable pageable){
        return ResponseEntity.ok(itemService.findAll(pageable));
    }

}

package com.wanted.challenge.domain.item.controller;


import com.wanted.challenge.domain.item.dto.request.ItemPurchaseRequestDTO;
import com.wanted.challenge.domain.item.dto.request.ItemRegisterRequestDTO;
import com.wanted.challenge.domain.item.dto.response.ItemDetailInfoResponseDTO;
import com.wanted.challenge.domain.item.dto.response.ItemPurchaseResponseDTO;
import com.wanted.challenge.domain.item.dto.response.ItemRegisterResponseDTO;
import com.wanted.challenge.domain.item.dto.response.ItemResponseDTO;
import com.wanted.challenge.domain.item.service.ItemService;
import com.wanted.challenge.global.api.ApiResponse;
import com.wanted.challenge.global.auth.AuthUser;
import com.wanted.challenge.global.auth.OptionalAuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    // 상품 등록
    @PostMapping()
    public ResponseEntity<ApiResponse<?>> registerItem(@Valid @RequestBody ItemRegisterRequestDTO itemRegisterRequestDTO,
                                                       @AuthUser Long userId) {
        ItemRegisterResponseDTO itemRegisterResponseDTO = itemService.registerItem(itemRegisterRequestDTO, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(itemRegisterResponseDTO, "상품 등록에 성공하였습니다."));
    }

    // 상품 조회
    @GetMapping()
    public ResponseEntity<ApiResponse<?>> findAllItems() {
        List<ItemResponseDTO> allItems = itemService.findAllItems();

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(allItems, "상품 조회를 성공했습니다."));
    }

    // 상품 상세조회
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<?>> findDetailItem(@PathVariable(name = "id") Long itemId,
                                                         @OptionalAuthUser Long userId) {
        ItemDetailInfoResponseDTO detailItem = itemService.findDetailItem(itemId, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(detailItem, "상품 조회를 성공했습니다."));
    }

    // 상품 구매하기
    @PostMapping("purchase")
    public ResponseEntity<ApiResponse<?>> purchaseItem(@Valid @RequestBody ItemPurchaseRequestDTO itemPurchaseRequestDTO,
                                                       @AuthUser Long userId) {
        ItemPurchaseResponseDTO itemPurchaseResponseDTO = itemService.purchaseItem(itemPurchaseRequestDTO, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(itemPurchaseResponseDTO, "아이템 구매 신청이 완료되었습니다."));
    }
}

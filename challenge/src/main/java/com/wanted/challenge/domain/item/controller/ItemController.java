package com.wanted.challenge.domain.item.controller;


import com.wanted.challenge.domain.exception.exception.MemberException;
import com.wanted.challenge.domain.exception.info.MemberExceptionInfo;
import com.wanted.challenge.domain.item.dto.request.ItemRegisterRequestDTO;
import com.wanted.challenge.domain.item.dto.response.ItemDetailInfoResponseDTO;
import com.wanted.challenge.domain.item.dto.response.ItemRegisterResponseDTO;
import com.wanted.challenge.domain.item.dto.response.ItemResponseDTO;
import com.wanted.challenge.domain.item.service.ItemService;
import com.wanted.challenge.global.api.ApiResponse;
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
                                                       @SessionAttribute(name = "userId", required = false) Long userId) {
        if (userId == null) {
            throw new MemberException(MemberExceptionInfo.NOT_FOUND_SESSION, "세션이 만료되었거나 유효하지 않습니다.");
        }
        ItemRegisterResponseDTO itemRegisterResponseDTO = itemService.registerItem(itemRegisterRequestDTO, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(itemRegisterRequestDTO, "상품 등록에 성공하였습니다."));
    }

    // 상품 조회
    @GetMapping()
    public ResponseEntity<ApiResponse<?>> findAllItems() {
        List<ItemResponseDTO> allItems = itemService.findAllItems();

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(allItems, "상품 조회를 성공했습니다."));
    }

    // 상품 상세조회
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<?>> findDetailItem(@PathVariable(name = "id") Long id) {
        ItemDetailInfoResponseDTO detailItem = itemService.findDetailItem(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(detailItem, "상품 조회를 성공했습니다."));
    }
}

package com.wanted.challenge.domain.item.service;

import com.wanted.challenge.domain.item.dto.request.ItemPurchaseRequestDTO;
import com.wanted.challenge.domain.item.dto.request.ItemRegisterRequestDTO;
import com.wanted.challenge.domain.item.dto.response.ItemDetailInfoResponseDTO;
import com.wanted.challenge.domain.item.dto.response.ItemPurchaseResponseDTO;
import com.wanted.challenge.domain.item.dto.response.ItemRegisterResponseDTO;
import com.wanted.challenge.domain.item.dto.response.ItemResponseDTO;

import java.util.List;

public interface ItemService {

    // 상품 등록
    ItemRegisterResponseDTO registerItem(ItemRegisterRequestDTO itemRegisterRequestDTO, Long id);

    // 모든 상품 조회
    List<ItemResponseDTO> findAllItems();

    // 특정 상품 조회
    ItemDetailInfoResponseDTO findDetailItem(Long itemId, Long userId);

    // 아이템 구매
    ItemPurchaseResponseDTO purchaseItem(ItemPurchaseRequestDTO itemPurchaseRequestDTO, Long id);

}

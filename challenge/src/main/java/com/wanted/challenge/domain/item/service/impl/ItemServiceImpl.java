package com.wanted.challenge.domain.item.service.impl;

import com.wanted.challenge.domain.exception.exception.ItemException;
import com.wanted.challenge.domain.exception.exception.MemberException;
import com.wanted.challenge.domain.exception.info.ItemExceptionInfo;
import com.wanted.challenge.domain.exception.info.MemberExceptionInfo;
import com.wanted.challenge.domain.item.dto.request.ItemPurchaseRequestDTO;
import com.wanted.challenge.domain.item.dto.request.ItemRegisterRequestDTO;
import com.wanted.challenge.domain.item.dto.response.ItemDetailInfoResponseDTO;
import com.wanted.challenge.domain.item.dto.response.ItemPurchaseResponseDTO;
import com.wanted.challenge.domain.item.dto.response.ItemRegisterResponseDTO;
import com.wanted.challenge.domain.item.dto.response.ItemResponseDTO;
import com.wanted.challenge.domain.item.entity.Item;
import com.wanted.challenge.domain.item.repository.ItemRepository;
import com.wanted.challenge.domain.item.service.ItemService;
import com.wanted.challenge.domain.member.entity.Member;
import com.wanted.challenge.domain.member.repository.MemberRepository;
import com.wanted.challenge.domain.transactionhistory.dto.response.TransactionHistoryResponseDTO;
import com.wanted.challenge.domain.transactionhistory.entity.TransactionHistory;
import com.wanted.challenge.domain.transactionhistory.repository.TransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    // 상품 등록
    @Override
    @Transactional
    public ItemRegisterResponseDTO registerItem(ItemRegisterRequestDTO itemRegisterRequestDTO, Long id) {
        Member currentMember = getCurrentMember(id);

        Item item = itemRegisterRequestDTO.toEntity(currentMember);

        Item savedItem = itemRepository.save(item);

        return ItemRegisterResponseDTO.toDTO(savedItem);
    }

    // 모든 상품 조회
    @Override
    @Transactional(readOnly = true)
    public List<ItemResponseDTO> findAllItems() {
        return itemRepository.findAllItems();
    }

    // 특정 상품 조회
    @Override
    @Transactional(readOnly = true)
    public ItemDetailInfoResponseDTO findDetailItem(Long itemId, Long userId) {
        Item item = itemRepository.findByIdFetchJoinMember(itemId)
                .orElseThrow(() -> new ItemException(ItemExceptionInfo.NOT_FOUND_ITEM, itemId + "번 상품이 존재하지 않습니다."));

        List<TransactionHistoryResponseDTO> transactionHistoryResponseDTOS = new ArrayList<>();
        // 회원이라면 거래내역 조회 진행
        if (userId != null){
            Member currentMember = getCurrentMember(userId);

            // 판매자라면 해당 상품의 거래 내역
            if(item.getMember() == currentMember){
                transactionHistoryResponseDTOS = transactionHistoryRepository.findTransactionHistoriesListByItem(item);
            }
            // 구매자라면 해당 상품의 구매 내역
            else{
                transactionHistoryResponseDTOS = transactionHistoryRepository.findTransactionHistoriesList(currentMember, item);
            }
        }

        return ItemDetailInfoResponseDTO.toDTO(item, transactionHistoryResponseDTOS);
    }

    // 아이템 구매하기
    @Override
    @Transactional
    public ItemPurchaseResponseDTO purchaseItem(ItemPurchaseRequestDTO itemPurchaseRequestDTO, Long id) {
        Member currentMember = getCurrentMember(id);

        Item item = itemRepository.findByIdFetchJoinMember(itemPurchaseRequestDTO.getId())
                .orElseThrow(() -> new ItemException(ItemExceptionInfo.NOT_FOUND_ITEM, currentMember.getId() + "번 유저가 " +id + "번 상품 구매 시도.(상품 존재하지 않음)"));

        // 본인의 상품은 구매 불가
        if (item.getMember() == currentMember){
            throw new ItemException(ItemExceptionInfo.DONT_PURCHASE_SELF_ITEM, currentMember.getId() + "번 유저가" + item.getId() + "구매 신청을 실패했습니다.(본인 물건 구매)");
        }

        // 한 번만 구매하기 위해서
        if (transactionHistoryRepository.existsByMemberAndItem(currentMember, item)) {
            throw new ItemException(ItemExceptionInfo.ALREADY_PURCHASE_ITEM, currentMember.getId() + "번 유저가" + item.getId() + " 구매를 재신청 했습니다.(중복 구매)");
        }

        // 재고 확인
        if (item.getQuantity() <= 0) {
            throw new ItemException(ItemExceptionInfo.NOT_ENOUGH_ITEM_QUANTITY, item.getId() + "번 상품의 재고가 부족합니다.(" + id + "번 유저 구매 실패)");
        }
        
        // 재고 감소
        item.decreaseQuantity();
        // 재고 0이하면 상품의 상태 변경
        if (item.getQuantity() <= 0) {
            item.changeSaleStatusReserved();
        }

        TransactionHistory transactionHistory = TransactionHistory.builder()
                .saleConfirmStatus(false)
                .purchaseConfirmStatus(false)
                .purchasePrice(item.getPrice())
                .item(item)
                .member(currentMember).build();
        transactionHistoryRepository.save(transactionHistory);

        return ItemPurchaseResponseDTO.toDTO(transactionHistory);
    }

    private Member getCurrentMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(MemberExceptionInfo.NOT_FOUNT_MEMBER, id + "번 유저가 존재하지 않습니다."));
    }
}

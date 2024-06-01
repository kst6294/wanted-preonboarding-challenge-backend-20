package com.wanted.challenge.domain.item.service.impl;

import com.wanted.challenge.domain.exception.exception.ItemException;
import com.wanted.challenge.domain.exception.exception.MemberException;
import com.wanted.challenge.domain.exception.info.ItemExceptionInfo;
import com.wanted.challenge.domain.exception.info.MemberExceptionInfo;
import com.wanted.challenge.domain.item.dto.request.ItemRegisterRequestDTO;
import com.wanted.challenge.domain.item.dto.response.ItemDetailInfoResponseDTO;
import com.wanted.challenge.domain.item.dto.response.ItemRegisterResponseDTO;
import com.wanted.challenge.domain.item.dto.response.ItemResponseDTO;
import com.wanted.challenge.domain.item.entity.Item;
import com.wanted.challenge.domain.item.repository.ItemRepository;
import com.wanted.challenge.domain.item.service.ItemService;
import com.wanted.challenge.domain.member.entity.Member;
import com.wanted.challenge.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

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
    public ItemDetailInfoResponseDTO findDetailItem(Long id) {
        Item item = itemRepository.findByIdFetchJoinMember(id)
                .orElseThrow(() -> new ItemException(ItemExceptionInfo.NOT_FOUND_ITEM, id + "번 상품이 존재하지 않습니다."));

        return ItemDetailInfoResponseDTO.toDTO(item);
    }

    private Member getCurrentMember(Long id){
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(MemberExceptionInfo.NOT_FOUNT_MEMBER, id + "번 유저가 존재하지 않습니다."));
    }
}

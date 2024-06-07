package com.wantedmarket.Item.service;

import com.wantedmarket.Deal.domain.Deal;
import com.wantedmarket.Deal.service.DealService;
import com.wantedmarket.Item.domain.Item;
import com.wantedmarket.Item.dto.ItemDto;
import com.wantedmarket.Item.exception.ItemException;
import com.wantedmarket.Item.repository.ItemRepository;
import com.wantedmarket.member.domain.Member;
import com.wantedmarket.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.wantedmarket.Item.exception.ItemErrorCode.INVALID_ID;
import static com.wantedmarket.Item.type.ItemStatus.ON_SALE;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemException(INVALID_ID));
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }
}

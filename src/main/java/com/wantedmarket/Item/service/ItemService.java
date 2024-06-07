package com.wantedmarket.Item.service;

import com.wantedmarket.Item.domain.Item;
import com.wantedmarket.Item.dto.ItemDto;
import com.wantedmarket.Item.exception.ItemException;
import com.wantedmarket.Item.repository.ItemRepository;
import com.wantedmarket.member.domain.Member;
import com.wantedmarket.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.wantedmarket.Item.exception.ItemErrorCode.INVALID_ID;
import static com.wantedmarket.Item.type.ItemStatus.ON_SALE;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final MemberService memberService;

    public ItemDto.Response addItem(String username, ItemDto.Request itemDto) {
        Member member = memberService.getMember(username);
        Item savedItem = itemRepository.save(
                Item.builder()
                        .itemName(itemDto.getItemName())
                        .price(itemDto.getPrice())
                        .itemStatus(ON_SALE)
                        .seller(member)
                        .build()
        );
        return ItemDto.Response.from(savedItem);
    }

    public List<ItemDto.Response> getItems() {
        return itemRepository.findAll()
                .stream()
                .map(ItemDto.Response::from)
                .collect(Collectors.toList());
    }

    public ItemDto.Response getItemDetail(Long id) {
        Item item = findById(id);
        return ItemDto.Response.from(item);
    }

    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemException(INVALID_ID));
    }
}

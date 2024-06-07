package com.wantedmarket.Item.facade;

import com.wantedmarket.Deal.domain.Deal;
import com.wantedmarket.Deal.service.DealService;
import com.wantedmarket.Item.domain.Item;
import com.wantedmarket.Item.dto.ItemDto;
import com.wantedmarket.Item.service.ItemService;
import com.wantedmarket.member.domain.Member;
import com.wantedmarket.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.wantedmarket.Item.type.ItemStatus.ON_SALE;

@Component
@RequiredArgsConstructor
public class ItemFacade {
    private final MemberService memberService;
    private final DealService dealService;
    private final ItemService itemService;

    public ItemDto.Response addItem(String username, ItemDto.Request itemDto) {
        Member member = memberService.getMember(username);
        Item savedItem = itemService.save(
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
        return itemService.findAll()
                .stream()
                .map(ItemDto.Response::from)
                .collect(Collectors.toList());
    }

    public ItemDto.Response getItemDetail(Long id) {
        Item item = itemService.findById(id);
        return ItemDto.Response.from(item);
    }

    public List<ItemDto.Response> getPurchasedItem(String username) {
        List<Deal> purchasedDeal = dealService.findAllByBuyer(username);
        return purchasedDeal.stream()
                .map(Deal::getItem)
                .map(ItemDto.Response::from)
                .collect(Collectors.toList());
    }

    public List<ItemDto.Response> getReservedItem(String username) {
        List<Deal> reservedDeal = dealService.findAllIsReserved(username);
        return reservedDeal.stream()
                .map(Deal::getItem)
                .map(ItemDto.Response::from)
                .collect(Collectors.toList());
    }

}

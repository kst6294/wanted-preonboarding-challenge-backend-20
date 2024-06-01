package com.market.wanted.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long add(ItemAddDto itemAddDto, Long id){
        Item item = Item.builder()
                .name(itemAddDto.getName())
                .price(itemAddDto.getPrice())
                .saleCount(itemAddDto.getCount())
                .sellerId(id)
                .build();
        itemRepository.save(item);
        return item.getId();
    }
}

package com.market.wanted.item;

import com.market.wanted.member.Member;
import com.market.wanted.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ItemTest {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName("제품 등록")
    @Transactional
    void add() {
        // given
        Item item = Item.builder()
                .name("name")
                .price(1000l)
                .saleCount(20l)
                .sellerId(1l)
                .build();

        // when
        Item save = itemRepository.save(item);

        // then
        assertEquals(item.getId(), save.getId());
    }
}

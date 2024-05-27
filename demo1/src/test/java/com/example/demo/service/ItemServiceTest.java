package com.example.demo.service;

import com.example.demo.dto.request.ItemBuy;
import com.example.demo.dto.response.ItemResponse;
import com.example.demo.entity.Item;
import com.example.demo.entity.Member;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.awt.print.Pageable;
import java.util.stream.Stream;

import static com.example.demo.entity.ItemState.AVAILABLE;
import static com.example.demo.entity.ItemState.RESERVED;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 아이템_등록(){
        //given
        Member member = memberRepository.findByEmail("jong")
                .orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 이메일입니다."));

        Item item = new Item("테스트아이템1", 10000, AVAILABLE, member);

        //when
        Item responseItem = itemRepository.save(item);

        //then
        assertEquals(item, responseItem);

    }

    @Test
    void 아이템_예약및구매(){
        //given
        ItemBuy itemBuy = new ItemBuy(2L, RESERVED);

        Item responseItem = itemRepository.findById(itemBuy.id())
                .orElseThrow();

        //when
        responseItem.changeItemState(itemBuy.itemState());


        //then
        Item result = itemRepository.findById(itemBuy.id())
                .orElseThrow();
        assertEquals(result, responseItem);

    }

    @Test
    void 아이템_상세조회(){
        //given
        Item item = itemRepository.findById(2L)
                .orElseThrow();

        //when
        ItemResponse itemResponse = new ItemResponse(item);

        //then
        assertEquals(itemResponse.name(), item.getName());
    }


    @Test
    void 아이템_페이징조회(){
        //given
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Item> item = itemRepository.findAll(pageable);

        //when
        Stream<ItemResponse> itemResponse = item.stream().map((s) -> new ItemResponse(s));

        //then
        assertEquals(itemResponse.count(), 10);
    }

}
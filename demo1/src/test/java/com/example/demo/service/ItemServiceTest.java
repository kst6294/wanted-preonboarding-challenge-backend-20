package com.example.demo.service;

import com.example.demo.dto.request.ItemBuy;
import com.example.demo.dto.response.ItemResponse;
import com.example.demo.entity.Buy;
import com.example.demo.entity.Item;
import com.example.demo.entity.Member;
import com.example.demo.exception.ItemBuyException;
import com.example.demo.repository.BuyRespository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private BuyRespository buyRespository;

    @Test
    void 아이템_등록(){
        //given
        Member sellMember = new Member("test1", "test1");
        Member sellReturnMember = memberRepository.save(sellMember);

        Item item = new Item("테스트아이템1", 10000, 10,  AVAILABLE, sellReturnMember);

        //when
        Item responseItem = itemRepository.save(item);

        //then
        assertEquals(item, responseItem);

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

    @Test
    void 사용자_아이템_구매하기_버튼(){
        //given
        Member sellMember = new Member("test1", "test1");
        Member sellReturnMember = memberRepository.save(sellMember);

        Member purcMember  = new Member("test2", "test2");
        Member purcResturnMember = memberRepository.save(purcMember);

        //아이템 생성
        Item item = new Item("테스트아이템1", 10000, 10,  AVAILABLE, sellReturnMember);
        Item itemResult = itemRepository.save(item);

        ItemBuy itemBuy = new ItemBuy(null,itemResult.getId(),RESERVED);

        //when
        boolean result = reservedItem(item, purcResturnMember, itemBuy);


        //then
        assertTrue(result);

    }






    @Test
    void 한_명당_1개_구매_예외(){
        //given
        Member sellMember = new Member("test1", "test1");
        Member sellReturnMember = memberRepository.save(sellMember);

        Member purcMember  = new Member("test2", "test2");
        Member purcResturnMember = memberRepository.save(purcMember);

        //아이템 생성
        Item item = new Item("테스트아이템1", 10000, 10,  AVAILABLE, sellReturnMember);
        Item itemResult = itemRepository.save(item);

        ItemBuy itemBuy = new ItemBuy(null,itemResult.getId(),RESERVED);

        //when
        reservedItem(item, purcResturnMember, itemBuy);


        //then
        ItemBuyException thrown = assertThrows(ItemBuyException.class, () -> {
            reservedItem(item, purcResturnMember, itemBuy);
        });

        assertTrue(thrown.getMessage().contains("한 명당 한개의 아이템만 구매할수 있습니다."));

    }
    @Test
    void 판매자와_구매자가_동일한_사람일_시_예외 (){
        //given
        Member sellMember = new Member("test1", "test1");
        Member sellReturnMember = memberRepository.save(sellMember);

        //아이템 생성
        Item item = new Item("테스트아이템1", 10000, 10,  AVAILABLE, sellReturnMember);
        Item itemResult = itemRepository.save(item);

        ItemBuy itemBuy = new ItemBuy(null,itemResult.getId(),RESERVED);

        //when & then
        ItemBuyException thrown = assertThrows(ItemBuyException.class, () -> {
            reservedItem(item, sellReturnMember, itemBuy);
        });

        assertTrue(thrown.getMessage().contains("판매자와 구매자가 동일한 사람"));

    }


    private boolean reservedItem(Item item, Member member, ItemBuy itemBuy){

        List<Buy> buyValue = buyRespository.findByPurchaseIdAndItem(member.getId(), item);

        if(buyValue.size() != 0){
            throw new ItemBuyException("한 명당 한개의 아이템만 구매할수 있습니다.");
        }

        //판매자와 구매자가 동일한 사람일 시 예외
        if(item.getMember().getId().equals(member.getId())){
            throw new ItemBuyException("판매자와 구매자가 동일한 사람");
        }

        Buy buy = new Buy(item.getMember().getId(), member.getId(), item.getPrice(), itemBuy.itemState(), item);

        return buyRespository.save(buy).getSellId().equals(buy.getSellId());

    }

}
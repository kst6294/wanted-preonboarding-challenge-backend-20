package com.example.demo.service;

import com.example.demo.dto.request.ItemBuy;
import com.example.demo.dto.request.ItemSave;
import com.example.demo.dto.response.ItemBuyList;
import com.example.demo.dto.response.ItemHistory;
import com.example.demo.dto.response.ItemResponse;
import com.example.demo.entity.Buy;
import com.example.demo.entity.Item;
import com.example.demo.entity.Member;
import com.example.demo.exception.ItemBuyException;
import com.example.demo.repository.BuyRespository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

import static com.example.demo.entity.ItemState.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final BuyRespository buyRespository;

    /*
     * 아이템 등록
     */
    public boolean itemSave(Authentication authentication, ItemSave itemSave){
        Member member = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 이메일입니다."));

        Item item = new Item(itemSave.name(), itemSave.price(), itemSave.quantity(), itemSave.itemState(), member);

        return item.getName().equals(itemRepository.save(item).getName());
    }

    /*
     * 아이템 구매 및 예약시 Buy 테이블 등록
     * 기본적으로 itemBuy.itemState()는 예약중.
     * 판매자와 구매자가 동일한 사람일시 예외
     *
     * 1. 사용자가 아이템 구매시 -> 예약중
     * 2. 판매자가 완료 버튼을 눌렀을 시 -> 완료
     * 3. Buy = 판매자 아이디, 구매자 아이디, 예약상태
     */

    public boolean itemBuy(Authentication authentication, ItemBuy itemBuy) {
        //Member의 id value
        Member member = findbyEmail(authentication);

        Item item = itemRepository.findById(itemBuy.item_id())
                .orElseThrow(() -> new ItemBuyException("존재하지 않는 상품입니다."));


        //0개일때 거래 불가능
        if(item.getQuantity() == 0){
            throw new ItemBuyException("아이템 잔여가 0개입니다.");
        }

        //===============================로직 시작 ==============================//
        //1. 사용자가 아이템 구매시 로직
        if(itemBuy.itemState().equals(RESERVED)){
            //한 명이 구매할 수 있는 수량은 1개에 대한 검증 로직
            //item_id와 purchase_id를 조회했을 때 값이 있으면 false
           return reservedItem(item, member, itemBuy);
        }

        //2. 사용자가 판매승인 하는 로직
        if(itemBuy.itemState().equals(SOLD)){
            return soldItem(member, itemBuy);
        }

        //3. 구매자가 구매확정 하는 로직
        if(itemBuy.itemState().equals(PURCHASE)){
            //판매승인 했을시 Item에서 현재 아이템 개수에서 -1
            if(purchaseItem(member, itemBuy)){
                return reduceItem(item);
            }

        }

        return false;
    }

    @Transactional(readOnly = true)
    public ItemResponse findOne(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemBuyException("존재하지 않는 상품입니다."));

        ItemResponse itemResponse = new ItemResponse(item);

        return itemResponse;
    }

    @Transactional(readOnly = true)
    public Stream<ItemResponse> findAll(Pageable pageable) {
        Page<Item> item = itemRepository.findAll(pageable);

        return item.stream().map((s) -> new ItemResponse(s));
    }

    @Transactional(readOnly = true)
    public Stream<ItemHistory> itemHistory(Authentication authentication, ItemBuy itemBuy){
        //구매자 아이디와 판매자 아이디로 비교
        //Member의 id value
        Member member = findbyEmail(authentication);

        Item item = itemRepository.findById(itemBuy.item_id())
                .orElseThrow(() -> new ItemBuyException("존재하지 않는 상품입니다."));

        List<Buy> buys = buyRespository.findHistory(member.getId(), item.getMember().getId());

        return buys.stream().map((s) -> new ItemHistory(s));

    }

    //구매 목록 item_state = SOLD And pucrchase_id = member.getId()'
    @Transactional(readOnly = true)
    public Stream<ItemBuyList> getPurchasedItems(Authentication authentication) {
        // 구매한 용품 조회 로직 구현
        Member member = findbyEmail(authentication);

        List<Buy> buys = buyRespository.findByPurchaseIdAndItemState(member.getId(), SOLD);
        return buys.stream().map((s) -> new ItemBuyList(s));
    }

    //예약중인 용품 item_state = "RESERVED" AND purchase_id = member.getId() and sell_id = member.getId();
    @Transactional(readOnly = true)
    public Stream<ItemBuyList> getReservedItems(Authentication authentication) {
        // 예약 중인 용품 조회 로직 구현
        Member member = findbyEmail(authentication);

        List<Buy> buys = buyRespository.findByReservedIdAndItemState(member.getId(), RESERVED);
        return buys.stream().map((s) -> new ItemBuyList(s));
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

    private boolean soldItem(Member member, ItemBuy itemBuy){
        //아이템 id + 판매자 id가 동일할때 진행 가능
        List<Buy> buy = buyRespository.findByIdAndSellId(itemBuy.id(), member.getId());

        if(buy.size() == 0){
            throw new ItemBuyException("아이템과 판매자 아이디가 동일하지 않습니다.");
        }
        buy.get(0).changeItemState(itemBuy.itemState());
        return true;

    }

    private boolean purchaseItem(Member member, ItemBuy itemBuy){
        //아이템 id + 구매자 id가 동일할때 진행 가능
        List<Buy> buy = buyRespository.findByIdAndPurchaseId(itemBuy.id(), member.getId());

        if(buy.size() == 0){
            throw new ItemBuyException("아이템과 구매자 아이디가 동일하지 않습니다.");
        }
        buy.get(0).changeItemState(itemBuy.itemState());
        return true;

    }

    private boolean reduceItem(Item item){
        item.reduceItem(1);

        //추가 판매 가능한 수량이 남아 있는 경우
        if(item.getQuantity() != 0){
            item.changeItemState(AVAILABLE);
        }

        //추가 판매가 불가능하고 현재 구매확정을 대기하고 있는 경우
        List<Buy> buy = buyRespository.findByItem(item);
        int checkPurchase = 0;
        for(Buy resultBuy : buy){
            //구매확정이 되었을 떄 checkPurchase 증가
            if(resultBuy.getItemState().equals(PURCHASE)){
                checkPurchase += 1;
            }
        }


        //item의 개수가 0 이거나 구매확정 갯수가 한개라도 있으면 예약 완료
        if(item.getQuantity()  == 0 && checkPurchase == 0){
            item.changeItemState(RESERVED);
        }

        if(item.getQuantity()  == 0 && checkPurchase != 0){
            item.changeItemState(SOLD);
        }

        return true;
    }


    private Member findbyEmail(Authentication authentication){
        Member member = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ItemBuyException("등록되지않은 이메일입니다."));

        return member;
    }

}

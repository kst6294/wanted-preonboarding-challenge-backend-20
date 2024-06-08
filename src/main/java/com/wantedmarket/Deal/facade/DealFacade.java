package com.wantedmarket.Deal.facade;

import com.wantedmarket.Deal.domain.Deal;
import com.wantedmarket.Deal.dto.DealDto;
import com.wantedmarket.Deal.exception.DealException;
import com.wantedmarket.Deal.service.DealService;
import com.wantedmarket.Item.domain.Item;
import com.wantedmarket.Item.service.ItemService;
import com.wantedmarket.member.domain.Member;
import com.wantedmarket.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.wantedmarket.Deal.exception.DealErrorCode.INVALID_STATUS;
import static com.wantedmarket.Deal.exception.DealErrorCode.IN_PROGRESS_OR_SOLD_OUT;
import static com.wantedmarket.Deal.exception.DealErrorCode.NOT_SELLER;
import static com.wantedmarket.Deal.exception.DealErrorCode.SAME_MEMBER;
import static com.wantedmarket.Deal.type.DealStatus.PENDING;
import static com.wantedmarket.Item.type.ItemStatus.ON_SALE;
import static com.wantedmarket.Item.type.ItemStatus.RESERVED;

@Component
@RequiredArgsConstructor
public class DealFacade {
    private final DealService dealService;
    private final ItemService itemService;
    private final MemberService memberService;

    @Transactional
    public DealDto purchase(String username, Long id) {
        Item item = itemService.findById(id);

        if (!item.getItemStatus().equals(ON_SALE)) {
            throw new DealException(IN_PROGRESS_OR_SOLD_OUT);
        }

        Member buyer = memberService.getMember(username);

        if (item.getSeller().getUsername().equals(username)) {
            throw new DealException(SAME_MEMBER);
        }

        item.reserve();

        Deal savedDeal = dealService.save(Deal.builder()
                .item(item)
                .buyer(buyer)
                .seller(item.getSeller())
                .dealStatus(PENDING)
                .build());

        return DealDto.from(savedDeal);
    }

    public List<DealDto> getTransaction(String username, Long id) {
        Member member = memberService.getMember(username);

        Deal deal = dealService.findByItemId(id);

        Member buyer = deal.getBuyer().equals(member) ? member : deal.getBuyer();
        Member seller = deal.getSeller().equals(member) ? member : deal.getSeller();

        List<Deal> dealList =
                dealService.findByBuyerAndSellerOrSellerAndBuyer(buyer, seller);

        return dealList.stream()
                .map(DealDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void approveTransaction(String username, Long id) {
        Member member = memberService.getMember(username);
        Deal deal = dealService.findById(id);

        if (!deal.getSeller().equals(member)) {
            throw new DealException(NOT_SELLER);
        }

        if (!deal.getItem().getItemStatus().equals(RESERVED)
                || !deal.getDealStatus().equals(PENDING)) {
            throw new DealException(INVALID_STATUS);
        }

        deal.getItem().complete();
        deal.approve();
    }
}

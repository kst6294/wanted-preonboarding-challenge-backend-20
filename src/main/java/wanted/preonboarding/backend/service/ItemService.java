package wanted.preonboarding.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.preonboarding.backend.domain.entity.Item;
import wanted.preonboarding.backend.domain.entity.Member;
import wanted.preonboarding.backend.domain.entity.Orders;
import wanted.preonboarding.backend.dto.request.ItemSaveRequest;
import wanted.preonboarding.backend.dto.response.ItemDetailResponse;
import wanted.preonboarding.backend.dto.response.ItemListResponse;
import wanted.preonboarding.backend.repository.ItemRepository;
import wanted.preonboarding.backend.repository.MemberRepository;
import wanted.preonboarding.backend.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    /**
     * 제품 등록
     */
    public void applyItem(final Long memberId, final ItemSaveRequest itemSaveRequest) {
        Member foundMember = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new IllegalArgumentException("회원 정보가 존재하지 않습니다.");
        });

        Item item = Item.from(itemSaveRequest, foundMember);
        itemRepository.save(item);
    }

    /**
     * 제품 목록 조회
     */
    @Transactional(readOnly = true)
    public ItemListResponse getItemList() {
        List<Item> foundItems = itemRepository.findAll();
        return ItemListResponse.from(foundItems);
    }

    /**
     * 제품 상세 조회 + 해당 제품의 판매자와 구매자의 거래 내역 조회 (해당 제품에 대한 거래 내역만 조회)
     * memberId != null 일 때, 거래 내역 조회
     * memberId == null 일 때, 거래 내역 조회 X
     */
    @Transactional(readOnly = true)
    public ItemDetailResponse getItem(final Long memberId, final Long itemId) {
        Item foundItem = itemRepository.findById(itemId).orElseThrow(() -> {
            throw new IllegalArgumentException("제품 정보가 존재하지 않습니다.");
        });

        //인증이 되지 않았다면, 거래 내역 조회 X
        if (memberId == null) {
            return ItemDetailResponse.from(foundItem);
        }

        //인증이 되었다면, 거래 내역 조회
        Optional<Orders> foundOrder = orderRepository.findOrderHistory(memberId, itemId);

        //거래 내역이 있는 경우, 거래 내역 정보를 응답 객체와 포함
        //거래 내역이 없는 경우, 거래 내역 정보를 응답 객체에서 제외
        return foundOrder.map(orders -> ItemDetailResponse.of(foundItem, orders))
                .orElseGet(() -> ItemDetailResponse.from(foundItem));
    }
}

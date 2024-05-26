package wanted.preonboarding.backend.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import wanted.preonboarding.backend.domain.entity.Item;
import wanted.preonboarding.backend.domain.entity.Member;
import wanted.preonboarding.backend.domain.entity.Orders;
import wanted.preonboarding.backend.dto.response.ItemDetailResponse;
import wanted.preonboarding.backend.repository.ItemRepository;
import wanted.preonboarding.backend.repository.OrderRepository;
import wanted.preonboarding.backend.service.ItemService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private ItemService itemService;

    private Orders order;
    private Item item;
    private Member member;

    @BeforeEach
    void init() {
        member = createMember();
        ReflectionTestUtils.setField(member, "id", 1L);

        item = createItem(member);
        ReflectionTestUtils.setField(item, "id", 1L);

        order = createOrder(item, member);
        ReflectionTestUtils.setField(order, "id", 1L);
    }

    @Test
    @DisplayName("성공 - 인증된 사용자가 제품 상세 정보 조회 성공, 거래내역 존재")
    void gettingItemSuccessWithAuthAndOrder() {
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(orderRepository.findOrderHistory(any(), any())).thenReturn(Optional.of(order));

        ItemDetailResponse result = itemService.getItem(1L, 1L);
        assertThat(result.getPrice()).isEqualTo(1000);
        assertThat(result.getStatus()).isEqualTo(Item.ItemStatus.FOR_SALE);
        assertThat(result.getOrder().getItemName()).isEqualTo("ItemName");
        assertThat(result.getOrder().getTotalPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("성공 - 인증된 사용자가 제품 상세 정보 조회 성공, 거래내역 부재")
    void gettingItemSuccessWithAuthAndWithoutOrder() {
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(orderRepository.findOrderHistory(any(), any())).thenReturn(Optional.ofNullable(null));

        ItemDetailResponse result = itemService.getItem(1L, 1L);
        assertThat(result.getPrice()).isEqualTo(1000);
        assertThat(result.getStatus()).isEqualTo(Item.ItemStatus.FOR_SALE);
        assertThat(result.getOrder()).isNull();
    }

    @Test
    @DisplayName("성공 - 인증되지 않은 사용자가 제품 상세 정보 조회 성공")
    void gettingItemSuccessWithoutAuth() {
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));

        ItemDetailResponse result = itemService.getItem(null, 1L);
        assertThat(result.getPrice()).isEqualTo(1000);
        assertThat(result.getStatus()).isEqualTo(Item.ItemStatus.FOR_SALE);
        assertThat(result.getOrder()).isNull();
    }

    private Orders createOrder(Item item, Member member) {
        return Orders.builder()
                .price(1000)
                .member(member)
                .item(item)
                .build();
    }

    private Item createItem(Member member) {
        return Item.builder()
                .name("ItemName")
                .price(1000)
                .status(Item.ItemStatus.FOR_SALE)
                .member(member)
                .build();
    }

    private Member createMember() {
        return Member.builder()
                .name("MemberName")
                .password("MemberPassword")
                .email("MemberEmail@email.com")
                .nickname("MemberNickname")
                .build();
    }
}

package wanted.preonboarding.backend.orders;

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
import wanted.preonboarding.backend.dto.request.OrderSaveRequest;
import wanted.preonboarding.backend.dto.request.OrderStatusUpdateRequest;
import wanted.preonboarding.backend.repository.ItemRepository;
import wanted.preonboarding.backend.repository.MemberRepository;
import wanted.preonboarding.backend.repository.OrderRepository;
import wanted.preonboarding.backend.service.OrderService;

import java.util.ConcurrentModificationException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private OrderService orderService;

    private Orders order;
    private Item item;
    private Member seller;
    private Member consumer;

    @BeforeEach
    void init() {
        seller = createMember();
        ReflectionTestUtils.setField(seller, "id", 1L);

        consumer = createMember();
        ReflectionTestUtils.setField(consumer, "id", 2L);

        item = createItem(seller);
        ReflectionTestUtils.setField(item, "id", 1L);

        order = createOrder(item, consumer);
        ReflectionTestUtils.setField(order, "id", 1L);
    }

    @Test
    @DisplayName("성공 - 제품 예약 성공")
    void addOrderSuccess() {
        OrderSaveRequest orderSaveRequest = new OrderSaveRequest(item.getId());

        when(memberRepository.findById(consumer.getId())).thenReturn(Optional.ofNullable(consumer));
        when(itemRepository.findById(item.getId())).thenReturn(Optional.ofNullable(item));

        orderService.addOrder(consumer.getId(), orderSaveRequest);
        verify(orderRepository).save(any());
    }

    @Test
    @DisplayName("실패 - 제품 구매 시 예약 상태면 실패")
    void addOrderFailedWhenItemStatusIsReserved() {
        Item otherItem = Item.builder()
                .name("ItemName")
                .price(1000)
                .stock(1)
                .status(Item.ItemStatus.RESERVED)
                .member(seller)
                .build();
        OrderSaveRequest orderSaveRequest = new OrderSaveRequest(otherItem.getId());

        when(memberRepository.findById(consumer.getId())).thenReturn(Optional.ofNullable(consumer));
        when(itemRepository.findById(otherItem.getId())).thenReturn(Optional.of(otherItem));

        assertThatThrownBy(() -> orderService.addOrder(consumer.getId(), orderSaveRequest))
                .isInstanceOf(ConcurrentModificationException.class);
    }

    @Test
    @DisplayName("실패 - 제품 구매 시 완료 상태면 실패")
    void buyingItemFailedWhenItemStatusIsComplete() {
        Item otherItem = Item.builder()
                .name("ItemName")
                .price(1000)
                .stock(1)
                .status(Item.ItemStatus.COMPLETE)
                .member(seller)
                .build();
        OrderSaveRequest orderSaveRequest = new OrderSaveRequest(otherItem.getId());

        when(memberRepository.findById(consumer.getId())).thenReturn(Optional.ofNullable(consumer));
        when(itemRepository.findById(otherItem.getId())).thenReturn(Optional.of(otherItem));

        assertThatThrownBy(() -> orderService.addOrder(consumer.getId(), orderSaveRequest))
                .isInstanceOf(ConcurrentModificationException.class);
    }

    @Test
    @DisplayName("실패 - 이미 구매한 제품이면 구매 실패")
    void buyingItemFailedWhenAlreadyOrdered() {
        OrderSaveRequest orderSaveRequest = new OrderSaveRequest(item.getId());

        when(memberRepository.findById(consumer.getId())).thenReturn(Optional.ofNullable(consumer));
        when(itemRepository.findById(item.getId())).thenReturn(Optional.ofNullable(item));
        when(orderRepository.findOrderHistory(consumer.getId(), item.getId())).thenReturn(Optional.of(order));

        assertThatThrownBy(() -> orderService.addOrder(consumer.getId(), orderSaveRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("성공 - 주문 상태를 판매 승인 상태로 변경 성공")
    void changeOrderStatusToApprovedSuccess() {
        OrderStatusUpdateRequest orderStatusUpdateRequest = new OrderStatusUpdateRequest(item.getId(), order.getId());

        when(memberRepository.findById(any())).thenReturn(Optional.of(seller));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(orderRepository.findById(any())).thenReturn(Optional.of(order));

        orderService.approveOrder(seller.getId(), orderStatusUpdateRequest);
        assertThat(order.getStatus()).isEqualTo(Orders.OrderStatus.APPROVED);
    }

    @Test
    @DisplayName("실패 - 제품 등록자 ID와 사용자 ID가 불일치하면 실패")
    void changeOrderStatusToApprovedFailed() {
        Member otherMember = createMember();
        ReflectionTestUtils.setField(otherMember, "id", 3L);
        Item otherItem = createItem(otherMember);
        ReflectionTestUtils.setField(otherItem, "id", 2L);

        OrderStatusUpdateRequest orderStatusUpdateRequest = new OrderStatusUpdateRequest(item.getId(), order.getId());

        when(memberRepository.findById(any())).thenReturn(Optional.of(otherMember));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));

        assertThatThrownBy(() -> orderService.approveOrder(otherItem.getId(), orderStatusUpdateRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("실패 - 주문 상태가 구매 예약 상태가 아니면 주문 상태 변경 실패")
    void changeOrderStatusToCompleteFailedWhenStatusIsNotReserved() {
        Orders approvedOrder = Orders.builder()
                .price(1000)
                .status(Orders.OrderStatus.APPROVED)
                .member(consumer)
                .item(item)
                .build();
        OrderStatusUpdateRequest orderStatusUpdateRequest = new OrderStatusUpdateRequest(item.getId(), approvedOrder.getId());

        when(memberRepository.findById(any())).thenReturn(Optional.of(seller));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));
        when(orderRepository.findById(any())).thenReturn(Optional.of(approvedOrder));

        assertThatThrownBy(() -> orderService.approveOrder(consumer.getId(), orderStatusUpdateRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("성공 - 주문 상태를 구매 확정 상태로 변경 성공")
    void changeOrderStatusToCompleteSuccess() {
        Orders approvedOrder = Orders.builder()
                .price(1000)
                .status(Orders.OrderStatus.APPROVED)
                .member(consumer)
                .item(item)
                .build();
        OrderStatusUpdateRequest orderStatusUpdateRequest = new OrderStatusUpdateRequest(item.getId(), approvedOrder.getId());

        when(orderRepository.findByOrdersId(any())).thenReturn(Optional.of(approvedOrder));
        orderService.completeOrder(consumer.getId(), orderStatusUpdateRequest);

        assertThat(approvedOrder.getStatus()).isEqualTo(Orders.OrderStatus.COMPLETE);
    }

    @Test
    @DisplayName("실패 - 주문 등록자 ID와 사용자 ID가 불일치하면 실패")
    void changeOrderStatusToCompleteFailed() {
        OrderStatusUpdateRequest orderStatusUpdateRequest = new OrderStatusUpdateRequest(item.getId(), order.getId());
        Member otherMember = createMember();
        ReflectionTestUtils.setField(otherMember, "id", 3L);

        when(orderRepository.findByOrdersId(any())).thenReturn(Optional.of(order));

        assertThatThrownBy(() -> orderService.completeOrder(otherMember.getId(), orderStatusUpdateRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("실패 - 주문 상태가 판매 승인 상태가 아니면 주문 상태 변경 실패")
    void changeOrderStatusToCompleteFailedWhenStatusIsNotApproved() {
        OrderStatusUpdateRequest orderStatusUpdateRequest = new OrderStatusUpdateRequest(item.getId(), order.getId());

        when(orderRepository.findByOrdersId(any())).thenReturn(Optional.of(order));

        assertThatThrownBy(() -> orderService.completeOrder(consumer.getId(), orderStatusUpdateRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private Orders createOrder(Item item, Member member) {
        return Orders.builder()
                .price(1000)
                .status(Orders.OrderStatus.RESERVED)
                .member(member)
                .item(item)
                .build();
    }

    private Item createItem(Member member) {
        return Item.builder()
                .name("ItemName")
                .price(1000)
                .stock(1)
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

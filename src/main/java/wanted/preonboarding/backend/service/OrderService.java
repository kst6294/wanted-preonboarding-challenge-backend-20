package wanted.preonboarding.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.preonboarding.backend.domain.entity.Item;
import wanted.preonboarding.backend.domain.entity.Member;
import wanted.preonboarding.backend.domain.entity.Orders;
import wanted.preonboarding.backend.dto.request.OrderSaveRequest;
import wanted.preonboarding.backend.dto.request.OrderStatusUpdateRequest;
import wanted.preonboarding.backend.dto.response.OrderListResponse;
import wanted.preonboarding.backend.dto.response.OrderResponse;
import wanted.preonboarding.backend.repository.ItemRepository;
import wanted.preonboarding.backend.repository.MemberRepository;
import wanted.preonboarding.backend.repository.OrderRepository;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    /**
     * 주문 추가
     */
    public void addOrder(final Long memberId, final OrderSaveRequest orderSaveRequest) {
        Member foundMember = findMember(memberId);
        Item foundItem = findItem(orderSaveRequest.getItemId());

        if (!foundItem.validateForSale()) {
            throw new ConcurrentModificationException("구매할 수 없는 상태입니다.");
        }

        Orders order = Orders.from(orderSaveRequest, foundMember, foundItem);
        orderRepository.save(order);
    }

    /**
     * 판매 승인 - 주문의 상태를 APPROVED로 변경
     */
    public void approveOrder(final Long sellerId, final OrderStatusUpdateRequest orderStatusUpdateRequest) {
        Member foundMember = findMember(sellerId);
        Item foundItem = findItem(orderStatusUpdateRequest.getItemId());

        //제품 등록자 ID와 사용자 ID가 일치하지 않으면 판매 승인 권한이 없는 것으로 간주
        if (!foundItem.getMember().getId().equals(foundMember.getId())) {
            throw new IllegalArgumentException("판매 승인 권한이 없습니다.");
        }

        Orders foundOrder = orderRepository.findById(orderStatusUpdateRequest.getOrderId()).orElseThrow(() -> {
            throw new IllegalArgumentException("주문 정보가 존재하지 않습니다.");
        });
        foundOrder.changeStatus(Orders.OrderStatus.APPROVED);
    }

    /**
     * 구매 승인 - 주문의 상태를 COMPLETE로 변경
     */
    public void completeOrder(final Long memberId, final OrderStatusUpdateRequest orderStatusUpdateRequest) {
        Orders foundOrder = orderRepository.findByOrdersId(orderStatusUpdateRequest.getOrderId()).orElseThrow(() -> {
            throw new IllegalArgumentException("주문 정보가 존재하지 않습니다.");
        });

        //주문 등록자가 아니면 구매 승인 권한이 아닌 것으로 간주
        if (!memberId.equals(foundOrder.getMember().getId())) {
            throw new IllegalArgumentException("구매 승인 권한이 없습니다.");
        }

        foundOrder.changeStatus(Orders.OrderStatus.COMPLETE);
    }

    /**
     * 거래 내역 조회 - 특정 제품에 대한 구매자와 판매자 간 거래 내역
     */
    @Transactional(readOnly = true)
    public OrderResponse getOrderHistory(final Long memberId, final Long itemId) {
        Optional<Orders> foundOrder = orderRepository.findOrderHistory(memberId, itemId);

        //거래 내역이 있으면 주문 정보를 담은 객체 반환
        //거래 내역이 없으면 빈 객체 반환
        return foundOrder.map(OrderResponse::from).orElseGet(OrderResponse::new);
    }

    /**
     *  사용자의 구매 확정 내역 조회
     */
    @Transactional(readOnly = true)
    public OrderListResponse getCompleteOrders(final Long memberId) {
        //구매 확정까지 완료되어 거래가 끝난 주문 정보 조회
        List<Orders> foundOrders = orderRepository.findOrdersWithStatus(memberId, Orders.OrderStatus.COMPLETE);
        return OrderListResponse.from(foundOrders);
    }

    /**
     * 사용자의 구매 예약 내역 조회
     * 주문 상태가 예약 상태인 주문 내역만 조회
     */
    @Transactional(readOnly = true)
    public OrderListResponse getReservedOrders(final Long memberId) {
        //사용자가 예약중인 제품 중 예약 상태인 제품만 조회
        List<Orders> foundOrders = orderRepository.findOrdersWithStatus(memberId, Orders.OrderStatus.RESERVED);
        return OrderListResponse.from(foundOrders);
    }

    /**
     * 사용자의 구매 예약 내역 조회
     * 주문 상태가 판매 승인 상태인 주문 내역만 조회
     */
    @Transactional(readOnly = true)
    public OrderListResponse getApprovedOrders(final Long memberId) {
        //사용자가 예약중인 제품 중 판매 승인 상태인 제품만 조회
        List<Orders> foundOrders = orderRepository.findOrdersWithStatus(memberId, Orders.OrderStatus.APPROVED);
        return OrderListResponse.from(foundOrders);
    }

    /**
     * 판매자가 받은 구매 신청 내역 (예약 상태) 조회
     */
    @Transactional(readOnly = true)
    public OrderListResponse getRequestedReservedOrders(final Long sellerId) {
        //판매 승인이 필요한 주문 정보 조회
        List<Orders> foundOrders = orderRepository.findRequestedOrders(sellerId, Orders.OrderStatus.RESERVED);
        return OrderListResponse.from(foundOrders);
    }

    //제품 단건 조회
    private Item findItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> {
            throw new IllegalArgumentException("제품 정보가 존재하지 않습니다.");
        });
    }

    //회원 단건 조회
    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> {
            throw new IllegalArgumentException("회원 정보가 존재하지 않습니다.");
        });
    }
}

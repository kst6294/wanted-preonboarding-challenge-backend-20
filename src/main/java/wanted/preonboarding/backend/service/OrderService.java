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
        //구매자 정보 조회 - 존재하지 않으면 예외
        Member foundMember = findMember(memberId);
        //주문할 제품 정보 조회 - 존재하지 않으면 예외
        Item foundItem = findItem(orderSaveRequest.getItemId());

        //제품 상태 검증 - 판매 가능(FOR_SALE) 상태가 아니면 주문 불가능
        if (!foundItem.validateForSale()) {
            throw new ConcurrentModificationException("제품이 매진되어 구매할 수 없는 상태입니다.");
        }

        //이미 구매한 제품인지 검증
        Optional<Orders> orderHistory = orderRepository.findOrderHistory(memberId, orderSaveRequest.getItemId());
        if (orderHistory.isPresent()) {
            throw new IllegalArgumentException("이미 구매한 제품입니다.");
        }

        //주문 저장
        Orders order = Orders.from(foundMember, foundItem);
        orderRepository.save(order);

        //제품 재고 감소
        foundItem.decreaseStock();
    }

    /**
     * 판매 승인 - 주문의 상태를 APPROVED로 변경
     */
    public void approveOrder(final Long sellerId, final OrderStatusUpdateRequest orderStatusUpdateRequest) {
        //판매자 정보 조회 - 존재하지 않으면 예외
        Member foundMember = findMember(sellerId);
        //주문할 제품 정보 조회 - 존재하지 않으면 예외
        Item foundItem = findItem(orderStatusUpdateRequest.getItemId());

        //제품 등록자 ID와 사용자 ID가 일치하지 않으면 판매 승인 권한이 없는 것으로 간주
        if (!foundItem.getMember().getId().equals(foundMember.getId())) {
            throw new IllegalArgumentException("판매 승인 권한이 없습니다.");
        }

        //주문 정보가 존재하는지 확인
        Orders foundOrder = orderRepository.findById(orderStatusUpdateRequest.getOrderId()).orElseThrow(() -> {
            throw new IllegalArgumentException("주문 정보가 존재하지 않습니다.");
        });

        //주문 상태가 구매 예약 상태(RESERVED)가 아니면 판매 승인 불가능
        if (!foundOrder.getStatus().equals(Orders.OrderStatus.RESERVED)) {
            throw new IllegalArgumentException("이미 판매 승인된 제품입니다.");
        }

        foundOrder.changeStatus(Orders.OrderStatus.APPROVED);
    }

    /**
     * 구매 확정 - 주문의 상태를 COMPLETE로 변경
     */
    public void completeOrder(final Long memberId, final OrderStatusUpdateRequest orderStatusUpdateRequest) {
        //주문 정보 조회 - 주문 정보가 없으면 예외
        Orders foundOrder = orderRepository.findByOrdersId(orderStatusUpdateRequest.getOrderId()).orElseThrow(() -> {
            throw new IllegalArgumentException("주문 정보가 존재하지 않습니다.");
        });

        //주문 등록자가 아니면 구매 승인 권한이 아닌 것으로 간주
        if (!memberId.equals(foundOrder.getMember().getId())) {
            throw new IllegalArgumentException("구매 승인 권한이 없습니다.");
        }

        //주문 상태가 판매 승인 상태(APPROVED)가 아니면 구매 확정 불가능
        if (!foundOrder.getStatus().equals(Orders.OrderStatus.APPROVED)) {
            throw new IllegalArgumentException("구매 확정을 할 수 없는 상태입니다.");
        }

        foundOrder.changeStatus(Orders.OrderStatus.COMPLETE);
    }

    /**
     * 거래 내역 조회 - 특정 제품에 대한 구매자와 판매자 간 거래 내역
     * 현재 사용자가 판매자인 경우, 제품에 대한 모든 구매자와의 거래 내역 조회
     * 현재 사용자가 구매자인 경우, 제품에 대한 판매자와의 거래 내역 조회
     */
    @Transactional(readOnly = true)
    public OrderListResponse getOrderHistory(final Long memberId, final Long itemId) {
        //제품 정보 조회
        Item foundItem = itemRepository.findItemById(itemId).orElseThrow(() -> {
            throw new IllegalArgumentException("제품 정보가 존재하지 않습니다.");
        });

        //현재 사용자가 제품 판매자면 구매자 목록 조회 및 반환
        if (foundItem.getMember().getId().equals(memberId)) {
            List<Orders> foundOrders = orderRepository.findOrdersByItemId(itemId);
            return OrderListResponse.from(foundOrders);
        }

        //현재 사용자가 제품 판매자가 아니면 제품에 대한 판매자와의 거래 내역 조회
        Optional<Orders> foundOrder = orderRepository.findOrderHistory(memberId, itemId);

        //거래 내역이 있으면 주문 정보를 담은 리스트 반환
        //거래 내역이 없으면 빈 리스트 반환
        return foundOrder.map(orders -> OrderListResponse.from(List.of(orders)))
                .orElseGet(() -> OrderListResponse.from(List.of()));
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
    private Item findItem(final Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> {
            throw new IllegalArgumentException("제품 정보가 존재하지 않습니다.");
        });
    }

    //회원 단건 조회
    private Member findMember(final Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> {
            throw new IllegalArgumentException("회원 정보가 존재하지 않습니다.");
        });
    }
}

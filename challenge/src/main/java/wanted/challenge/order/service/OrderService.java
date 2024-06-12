package wanted.challenge.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wanted.challenge.goods.entity.Goods;
import wanted.challenge.mypage.dto.response.MyPageResponseDto;
import wanted.challenge.mypage.entity.Member;
import wanted.challenge.mypage.mapper.MyPageMapper;
import wanted.challenge.mypage.repository.MemberRepository;
import wanted.challenge.order.entity.OrderStatus;
import wanted.challenge.order.entity.Orders;
import wanted.challenge.order.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final MyPageMapper mapper;

    //주문 생성
    public Orders createdOrder(Goods goods, Member buyer, int quantity){
        Orders order = new Orders();
        order.setGoodsName(goods.getGoodsName()); // 퍼포먼스
        order.setOrderPrice(goods.getGoodsPrice() * quantity);
        order.setQuantity(quantity);
        order.setBuyer(buyer);
        order.setGoods(goods);
        return orderRepository.save(order);
    }

    // 판매자-구매자 주문 리스트 조회
    public List<Orders> getOrderList(Long sellerId, Long buyerId) {
        return orderRepository.findByBuyer_MemberIdAndGoods_Seller_MemberId(buyerId, sellerId);
    }

    // 주문 리스트
    public List<Orders> getSellOrderList(Long memberId) {
        return orderRepository.findByGoods_Seller_MemberId(memberId);
    }

    public List<Orders> getBuyOrderList(Long memberId) {
        return orderRepository.findByBuyer_MemberId(memberId);
    }

    // 판매 물품 상세 주문 내역
    public MyPageResponseDto.sellOrderDetail getSellerOrderDetail(Long memberId, Long orderId) throws Exception {
        Optional<Orders> order = orderRepository.findById(orderId);
        Optional<Member> member = memberRepository.findById(memberId);
        if (order.isEmpty()) {
            //없는 주문 예외 처리
            throw new Exception("해당 주문이 존재하지 않습니다.");
        }
        if (member.isEmpty()) {
            //없는 회원 예외 처리
            throw new Exception("해당 회원이 존재하지 않습니다.");
        }
        return mapper.orderToSellOrderDetail(order.get(), member.get(), order.get().getGoods());

    }

    public MyPageResponseDto.buyOrderDetail getBuyerOrderDetail(Long memberId, Long orderId) throws Exception {
        Optional<Orders> order = orderRepository.findById(orderId);
        Optional<Member> member = memberRepository.findById(memberId);
        if (order.isEmpty()) {
            //없는 주문 예외 처리
            throw new Exception("해당 주문이 존재하지 않습니다.");
        }
        if (member.isEmpty()) {
            //없는 회원 예외 처리
            throw new Exception("해당 회원이 존재하지 않습니다.");
        }
        return mapper.orderToBuyOrderDetail(order.get(), order.get().getGoods());
    }

    // 판매승인
    public void sellConfirm(Long sellerId, Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));
        // 판매자와 주문이 일치하는지 확인
        if (!order.getGoods().getSeller().getMemberId().equals(sellerId)) {
            throw new IllegalArgumentException("해당 주문과 판매자가 일치하지 않습니다.");
        }
        if (OrderStatus.ORDER != order.getOrderStatus()) {
            throw new IllegalArgumentException("주문 상태가 ORDER가 아닙니다.");
        }
        // 판매 승인 시간 설정, 상태 변경
        order.setConfirmDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.CONFIRM);
        orderRepository.save(order);
    }


    // 구매 확정
    public void buyFinish(Long buyerId, Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));

        // 구매자와 주문이 일치하는지 확인
        if (!order.getBuyer().getMemberId().equals(buyerId)) {
            throw new IllegalArgumentException("해당 주문과 판매자가 일치하지 않습니다.");
        }
        if (OrderStatus.CONFIRM != order.getOrderStatus()) {
            throw new IllegalArgumentException("주문 상태가 CONFIRM이 아닙니다.");
        }
        // 구매 확정 시간 설정, 상태 변경
        order.setFinishDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.FINISH);
        orderRepository.save(order);
    }
}

package wanted.challenge.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wanted.challenge.mypage.dto.response.MyPageResponseDto;
import wanted.challenge.mypage.entity.Member;
import wanted.challenge.mypage.entity.Orders;
import wanted.challenge.mypage.mapper.MyPageMapper;
import wanted.challenge.mypage.repository.MemberRepository;
import wanted.challenge.mypage.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final MyPageMapper mapper;

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
        return mapper.orderToBuyOrderDetail(order.get(), member.get(), order.get().getGoods());
    }

    // 판매승인
    public void sellConfirm(Long sellerId, Long orderId) {
        // 판매자와 주문이 일치하는지 확인
        Optional<Orders> order = orderRepository.findById(orderId);
        if (order.isPresent() && order.get().getGoods().getSeller().getMemberId().equals(sellerId)) {
            //order의 상태가 order인 경우만 comfirm으로 변경
            if (!order.get().getOrderStatus().equals("order")) {
                throw new IllegalArgumentException("주문 상태가 order가 아닙니다.");
            }
            order.get().setOrderStatus("comfirm");
            orderRepository.save(order.get());
            return;
        }
        // 판매자와 주문이 일치하지 않는다면 예외 처리
        throw new IllegalArgumentException("해당 주문과 판매자가 일치하지 않습니다.");

    }

    // 구매 확정
    public void buyFinish(Long buyerId, Long orderId) {
        // 구매자와 주문이 일치하는지 확인
        Optional<Orders> order = orderRepository.findById(orderId);
        if (order.isPresent() && order.get().getBuyer().getMemberId().equals(buyerId)) {
            //order의 상태가 comfirm인 경우만 finish로 변경
            if (!order.get().getOrderStatus().equals("comfirm")) {
                throw new IllegalArgumentException("주문 상태가 comfirm이 아닙니다.");
            }
            order.get().setOrderStatus("finish");
            orderRepository.save(order.get());
            return;
        }
        // 구매자와 주문이 일치하지 않는다면 예외 처리
        throw new IllegalArgumentException("해당 주문과 구매자가 일치하지 않습니다.");
    }
}

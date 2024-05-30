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
    public void sellConfirm() {
    }

    // 구매 확정
    public void buyFinish() {
    }
}

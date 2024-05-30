package wanted.challenge.goods.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wanted.challenge.goods.dto.response.GoodsResponseDto;
import wanted.challenge.goods.entity.Goods;
import wanted.challenge.goods.mapper.GoodsMapper;
import wanted.challenge.goods.repository.GoodsRepository;
import wanted.challenge.mypage.entity.Member;
import wanted.challenge.mypage.entity.Orders;
import wanted.challenge.mypage.repository.MemberRepository;
import wanted.challenge.mypage.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoodsService {
    private final MemberRepository memberRepository;
    private final GoodsRepository goodsRepository;
    private final OrderRepository orderRepository;
    private final GoodsMapper mapper;

    public String createGoods(Long sellerId,  Goods goods) {
        Member seller = memberRepository.findById(sellerId).orElseThrow(() -> new IllegalArgumentException("해당 판매자가 존재하지 않습니다."));
        goods.setSeller(seller);
        goodsRepository.save(goods);
        return "상품등록완료";
    }

    public List<Goods> getGoodsList() {
        return goodsRepository.findAll();
    }

    public GoodsResponseDto.GoodsDetail getGoodsDetail(Long goodsId, Long buyerId) {
        Goods goods = goodsRepository.findById(goodsId).orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        // 거래내역이 있는지 확인
        List<Orders> tradeList = getTrades(buyerId, goods);

        return mapper.toGoodsDetail(goods, tradeList);
    }

    private List<Orders> getTrades(Long buyerId, Goods goods) {
        log.info("buyerId: " + buyerId);
        log.info("sellerId: " + goods.getSeller().getMemberId());
        return orderRepository.findByBuyer_MemberIdAndGoods_Seller_MemberId(buyerId, goods.getSeller().getMemberId());
    }

    public Goods updateGoods(long goodsId, Goods editedGoods) {
        Goods goods = goodsRepository.findById(goodsId).orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        goods.setGoodsName(editedGoods.getGoodsName());
        goods.setGoodsPrice(editedGoods.getGoodsPrice());
        goods.setReservedStatus(editedGoods.getReservedStatus());
        goods.setQuantity(editedGoods.getQuantity());

        // 상품에 연결된 판매자 정보도 업데이트
        if (editedGoods.getSeller() != null) {
            goods.setSeller(editedGoods.getSeller());
        }

        return goodsRepository.save(goods);
    }

    public String deleteGoods() {
        return "삭제완료";
    }
}

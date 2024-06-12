package wanted.challenge.goods.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wanted.challenge.goods.dto.request.GoodsRequestDto;
import wanted.challenge.goods.dto.response.GoodsResponseDto;
import wanted.challenge.goods.dto.response.GoodsResponseDto.GoodsDetail;
import wanted.challenge.goods.entity.Goods;
import wanted.challenge.goods.entity.GoodsStatus;
import wanted.challenge.mypage.dto.response.MyPageResponseDto;
import wanted.challenge.order.entity.Orders;

import java.util.ArrayList;
import java.util.List;

import static wanted.challenge.goods.dto.response.GoodsResponseDto.GoodsListItem;
import static wanted.challenge.goods.dto.response.GoodsResponseDto.UpdateGoods;
import static wanted.challenge.mypage.mapper.MyPageMapper.toOrderStatusResponse;
import static wanted.challenge.mypage.mapper.MyPageMapper.toOrderStatusTime;

@Slf4j
@Component
public class GoodsMapper {
    /**
     * 요청 mapper
     */
    public Goods toGoods(GoodsRequestDto.CreateGoods createGoods) {
        Goods goods = new Goods();
        goods.setGoodsName(createGoods.name());
        goods.setGoodsPrice(createGoods.price());
        goods.setQuantity(createGoods.quantity());
        return goods;
    }

    /**
     * 응답 mapper
     */

    public List<GoodsListItem> toGoodsList(List<Goods> goodsList) {
        List<GoodsListItem> list = new ArrayList<>();
        for (Goods goods : goodsList) {
            list.add(toGoodsItem(goods));
        }
        return list;
    }

    public GoodsListItem toGoodsItem(Goods goods) {
        return new GoodsListItem(
                goods.getGoodsId(),
                goods.getGoodsName(),
                goods.getGoodsPrice(),
                toGoodsStatus(goods.getReservedStatus()),
                goods.getQuantity()
        );

    }

    public GoodsDetail toGoodsDetail(Goods goods, List<Orders> ordersList) {
        List<MyPageResponseDto.Trade> tradeList = new ArrayList<>();
        if (!ordersList.isEmpty()) {
            log.info("거래내역이 있습니다.");
            // 거래내역이 있는지 확인
            for (Orders trade : ordersList) {
                tradeList.add(toTradeResponse(trade));
            }
        }
        return new GoodsDetail(
                goods.getGoodsId(),
                goods.getGoodsName(),
                goods.getGoodsPrice(),
                toGoodsStatus(goods.getReservedStatus()),
                goods.getQuantity(),
                goods.getCreatedAt(),
                tradeList
        );
    }

    public UpdateGoods toUpdateGoods(Goods goods) {
        return new UpdateGoods(
                goods.getGoodsId(),
                goods.getGoodsName(),
                goods.getGoodsPrice(),
                toGoodsStatus(goods.getReservedStatus()),
                goods.getQuantity(),
                goods.getCreatedAt()
        );
    }

    // 한 제품의 거래내역
    private MyPageResponseDto.Trade toTradeResponse(Orders trade) {

        return new MyPageResponseDto.Trade(
                trade.getOrderId(),
                toOrderStatusTime(trade.getOrderStatus(), trade),
                toOrderStatusResponse(trade.getOrderStatus()),
                trade.getGoods().getGoodsPrice(),
                trade.getQuantity()
        );
    }

    public static GoodsResponseDto.GoodsStatus toGoodsStatus(GoodsStatus status) {
        return switch (status) {
            case SALE -> GoodsResponseDto.GoodsStatus.판매중;
            case RESERVED -> GoodsResponseDto.GoodsStatus.예약중;
            case SOLD -> GoodsResponseDto.GoodsStatus.판매완료;
            default -> throw new IllegalArgumentException("올바르지 않은 상품 상태입니다.");
        };
    }
}

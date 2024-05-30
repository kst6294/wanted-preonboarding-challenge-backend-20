package wanted.challenge.goods.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import wanted.challenge.goods.dto.request.GoodsRequestDto;
import wanted.challenge.goods.dto.response.GoodsResponseDto;
import wanted.challenge.goods.dto.response.GoodsResponseDto.GoodsDetail;
import wanted.challenge.goods.entity.Goods;
import wanted.challenge.mypage.entity.Orders;

import java.util.ArrayList;
import java.util.List;

import static wanted.challenge.goods.dto.response.GoodsResponseDto.*;

@Slf4j
@Component
public class GoodsMapper {
    /**
     * 요청 mapper
     */
    public Goods toGoods(GoodsRequestDto.CreateGoods createGoods) {
        Goods goods = new Goods();
        goods.setGoodsName(createGoods.getName());
        goods.setGoodsPrice(createGoods.getPrice());
        goods.setQuantity(createGoods.getQuantity());
        return goods;
    }

    /**
     * 응답 mapper
     */
    public List<GoodsList> toGoodsList(List<Goods> goodsList) {
        List<GoodsList> list = new ArrayList<>();
        for (Goods goods : goodsList) {
            list.add(toGoodsItem(goods));
        }
        return  list;
    }

    public GoodsList toGoodsItem(Goods goods) {
        GoodsList goodsList = new GoodsList();
        goodsList.setGoodsId(goods.getGoodsId());
        goodsList.setGoodsName(goods.getGoodsName());
        goodsList.setGoodsPrice(goods.getGoodsPrice());
        goodsList.setReservedStatus(goods.getReservedStatus());
        goodsList.setQuantity(goods.getQuantity());
        return goodsList;

    }

    public GoodsDetail toGoodsDetail(Goods goods, List<Orders> ordersList) {
        GoodsDetail goodsDetail = new GoodsDetail();
        goodsDetail.setGoodsId(goods.getGoodsId());
        goodsDetail.setGoodsName(goods.getGoodsName());
        goodsDetail.setGoodsPrice(goods.getGoodsPrice());
        goodsDetail.setReservedStatus(goods.getReservedStatus());
        goodsDetail.setQuantity(goods.getQuantity());
        goodsDetail.setCreatedAt(goods.getCreatedAt());
        log.info(goodsDetail.getGoodsName());
        List<GoodsDetail.Trade> tradeList = new ArrayList<>();
        log.info("거래내역의 수: " + ordersList.size());
        if(!ordersList.isEmpty()) {
            log.info("거래내역이 있습니다.");
            // 거래내역이 있는지 확인
            for (Orders trade : ordersList) {
                tradeList.add(toTradeResponse(trade));
            }
        }
        goodsDetail.setTradeList(tradeList);
        return goodsDetail;
    }
    public UpdateGoods toUpdateGoods(Goods goods) {
        UpdateGoods updateGoods = new UpdateGoods();
        updateGoods.setGoodsId(goods.getGoodsId());
        updateGoods.setGoodsName(goods.getGoodsName());
        updateGoods.setGoodsPrice(goods.getGoodsPrice());
        updateGoods.setReservedStatus(goods.getReservedStatus());
        updateGoods.setQuantity(goods.getQuantity());
        updateGoods.setCreatedAt(goods.getCreatedAt());
        return updateGoods;
    }

    private GoodsDetail.Trade toTradeResponse(Orders trade) {
        GoodsDetail.Trade tradeResponse = new GoodsDetail.Trade();
        tradeResponse.setOrderId(trade.getOrderId());
        tradeResponse.setTradeStatus(trade.getOrderStatus());
        switch (trade.getOrderStatus()) {
            case "order":
                tradeResponse.setTradeStatus("승인대기");
                tradeResponse.setTradeAt(trade.getOrderDate());
                break;
            case "comfirm":
                tradeResponse.setTradeStatus("확정대기");
                tradeResponse.setTradeAt(trade.getConfirmDate());
                break;
            case "finish":
                tradeResponse.setTradeStatus("완료");
                tradeResponse.setTradeAt(trade.getFinishDate());
                break;
        }
        log.info("거래금액: " + trade.getOrderPrice());
        tradeResponse.setTradePrice(trade.getOrderPrice());
        tradeResponse.setQuantity(trade.getQuantity());
        return tradeResponse;
    }

}

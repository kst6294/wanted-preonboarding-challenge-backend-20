package wanted.challenge.goods.mapper;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import wanted.challenge.goods.dto.response.GoodsResponseDto;
import wanted.challenge.goods.dto.response.GoodsResponseDto.GoodsDetailResponse;
import wanted.challenge.goods.entity.Goods;

import java.util.ArrayList;
import java.util.List;

import static wanted.challenge.goods.dto.response.GoodsResponseDto.*;

@Component
public class GoodsMapper {
    public List<GoodsListResponse> toGoodsListResponse(List<Goods> goodsList) {
        List<GoodsListResponse> list = new ArrayList<>();
        for (Goods goods : goodsList) {
            list.add(toGoodsItemResponse(goods));
        }
        return  list;
    }

    public GoodsListResponse toGoodsItemResponse(Goods goods) {
        return new GoodsListResponse();
    }

    public GoodsDetailResponse toGoodsDetailResponse() {
        return new GoodsDetailResponse();
    }
}

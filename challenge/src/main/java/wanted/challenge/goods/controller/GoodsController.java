package wanted.challenge.goods.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wanted.challenge.goods.dto.response.GoodsResponseDto;
import wanted.challenge.goods.mapper.GoodsMapper;
import wanted.challenge.goods.service.GoodsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/goods")
public class GoodsController {

    private final GoodsService goodsService;
    private final GoodsMapper mapper;

    @GetMapping
    public List<GoodsResponseDto.GoodsListResponse> getGoodsList() {
        return mapper.toGoodsListResponse(goodsService.getGoodsList());

    }
    @GetMapping("/{goods_id}")
    public GoodsResponseDto.GoodsDetailResponse getGoodsDetail() {
        return mapper.toGoodsDetailResponse();
    }
    @PostMapping
    public String createGoods() {
        return goodsService.createGoods();
    }
    @PatchMapping("/{goods_id}")
    public GoodsResponseDto.GoodsDetailResponse updateGoods() {
        return mapper.toGoodsDetailResponse();
    }
    @DeleteMapping("/{goods_id}")
    public String deleteGoods() {
        return goodsService.deleteGoods();
    }

    @PostMapping("/{goods_id}/order")
    public GoodsResponseDto.GoodsOrderResponse orderGoods() {
        return new GoodsResponseDto.GoodsOrderResponse();
    }
}

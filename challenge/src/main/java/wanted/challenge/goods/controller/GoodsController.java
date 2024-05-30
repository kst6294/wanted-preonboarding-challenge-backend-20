package wanted.challenge.goods.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import wanted.challenge.goods.dto.request.GoodsRequestDto;
import wanted.challenge.goods.dto.response.GoodsResponseDto;
import wanted.challenge.goods.entity.Goods;
import wanted.challenge.goods.mapper.GoodsMapper;
import wanted.challenge.goods.service.GoodsService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/goods")
public class GoodsController {

    private final GoodsService goodsService;
    private final GoodsMapper mapper;

    @GetMapping
    public List<GoodsResponseDto.GoodsList> getGoodsList() {
        return mapper.toGoodsList(goodsService.getGoodsList());
    }

    @GetMapping("/{goods_id}")
    public GoodsResponseDto.GoodsDetail getGoodsDetail(
            @RequestHeader(name = "memberId", required = false ) Long memberId,
            @PathVariable("goods_id") Long goodsId) {
        return goodsService.getGoodsDetail(goodsId, memberId);
    }

    @PostMapping
    public String createGoods(
            @RequestHeader("memberId") Long memberId,
            @RequestBody GoodsRequestDto.CreateGoods createGoods) {
        return goodsService.createGoods(memberId, mapper.toGoods(createGoods));
    }

    @PatchMapping("/{goods_id}")
    public GoodsResponseDto.UpdateGoods updateGoods(
            @RequestHeader("memberId") Long memberId,
            @RequestBody GoodsRequestDto.CreateGoods editedGoods) {
        return mapper.toUpdateGoods(goodsService.updateGoods(memberId, mapper.toGoods(editedGoods)));
    }


    @DeleteMapping("/{goods_id}")
    public String deleteGoods() {
        return goodsService.deleteGoods();
    }

    @PostMapping("/{goods_id}/order")
    public GoodsResponseDto.GoodsOrder orderGoods(
            @RequestHeader("memberId") Long memberId,
            @PathVariable("goods_id") Long goodsId,
            @RequestBody int quantity) {
        return new GoodsResponseDto.GoodsOrder();
    }
}

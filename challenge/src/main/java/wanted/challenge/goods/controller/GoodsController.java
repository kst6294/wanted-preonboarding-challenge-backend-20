package wanted.challenge.goods.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import wanted.challenge.aop.api.ApiResponse;
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
    public ApiResponse<List<GoodsResponseDto.GoodsList>> getGoodsList() {
        List<GoodsResponseDto.GoodsList> goodsList = mapper.toGoodsList(goodsService.getGoodsList());
        return ApiResponse.success(goodsList);
    }

    @GetMapping("/{goods_id}")
    public ApiResponse<GoodsResponseDto.GoodsDetail> getGoodsDetail(
            @RequestHeader(name = "memberId", required = false) Long memberId,
            @PathVariable("goods_id") Long goodsId) {
        GoodsResponseDto.GoodsDetail goodsDetail = goodsService.getGoodsDetail(goodsId, memberId);
        return ApiResponse.success(goodsDetail);
    }

    @PostMapping
    public ApiResponse<String> createGoods(
            @RequestHeader("memberId") Long memberId,
            @RequestBody GoodsRequestDto.CreateGoods createGoods) {
        goodsService.createGoods(memberId, mapper.toGoods(createGoods));
        return ApiResponse.success();
    }

    @PatchMapping("/{goods_id}")
    public ApiResponse<GoodsResponseDto.UpdateGoods> updateGoods(
            @RequestHeader("memberId") Long memberId,
            @RequestBody GoodsRequestDto.CreateGoods editedGoods) {
        GoodsResponseDto.UpdateGoods updateGoods = mapper.toUpdateGoods(goodsService.updateGoods(memberId, mapper.toGoods(editedGoods)));
        return ApiResponse.success(updateGoods);

    }


    @DeleteMapping("/{goods_id}")
    public ApiResponse<String> deleteGoods() {
        goodsService.deleteGoods();
        return ApiResponse.success();
    }

    @PostMapping("/{goods_id}/order")
    public ApiResponse<GoodsResponseDto.GoodsOrder> orderGoods(
            @RequestHeader("memberId") Long memberId,
            @PathVariable("goods_id") Long goodsId,
            @RequestBody int quantity) {
        /**
         * TODO: 상품 주문
         */
        GoodsResponseDto.GoodsOrder goodsOrder = new GoodsResponseDto.GoodsOrder(
                goodsService.orderGoods(memberId, goodsId, quantity)
        );
        return ApiResponse.success(goodsOrder);
    }
}

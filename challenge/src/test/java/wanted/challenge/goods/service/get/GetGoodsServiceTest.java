package wanted.challenge.goods.service.get;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wanted.challenge.exception.custom.GoodsNotFoundException;
import wanted.challenge.goods.dto.response.GoodsResponseDto;
import wanted.challenge.goods.entity.Goods;
import wanted.challenge.goods.service.GoodsServiceTest;
import wanted.challenge.mypage.entity.Member;
import wanted.challenge.order.entity.Orders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GetGoodsServiceTest extends GoodsServiceTest {

    @Test
    @DisplayName("상품 상세 조회 성공 - 구매자가 존재함")
    void success_getEverything() {
        // given
        Long sellerId = 1L;
        Long goodsId = 1L;
        Long buyerId = 2L;
        Member seller = createTestSeller(sellerId);
        Member buyer = createTestBuyer(buyerId);
        Goods goods = createTestGoods(goodsId, seller);

        List<Orders> testTradeList = createTestTradeList(buyer, goods);

        GoodsResponseDto.GoodsDetail testGoodsDetail = mapper.toGoodsDetail(goods, testTradeList);
        // mocking
        given(goodsRepository.findById(anyLong())).willReturn(Optional.of(goods));
        given(orderService.getOrderList(anyLong(), anyLong())).willReturn(testTradeList);
        // when
        GoodsResponseDto.GoodsDetail goodsDetail = goodsService.getGoodsDetail(goodsId, buyerId);
        // then
        assertEquals(goods.getGoodsId(), goodsDetail.goodsId());
        assertEquals(goods.getGoodsName(), goodsDetail.goodsName());
        assertEquals(goods.getGoodsPrice(), goodsDetail.goodsPrice());
        assertEquals(goods.getQuantity(), goodsDetail.quantity());
        assertEquals(testTradeList.size(), goodsDetail.tradeList().size());
        verify(goodsRepository, times(1)).findById(anyLong());
        verify(orderService, times(1)).getOrderList(anyLong(), anyLong());
    }

    @Test
    @DisplayName("상품 상세 조회 성공 - 구매자가 존재하나 구매 이력이 없음")
    void success_getGoodsDetail() {
        // given
        Long sellerId = 1L;
        Long goodsId = 1L;
        Long buyerId = 2L;
        Member seller = createTestSeller(sellerId);
        Goods goods = createTestGoods(goodsId, seller);

        // mocking
        given(goodsRepository.findById(anyLong())).willReturn(Optional.of(goods));
        // when
        GoodsResponseDto.GoodsDetail goodsDetail = goodsService.getGoodsDetail(goodsId, buyerId);
        // then
        assertEquals(goods.getGoodsId(), goodsDetail.goodsId());
        assertEquals(goods.getGoodsName(), goodsDetail.goodsName());
        assertEquals(goods.getGoodsPrice(), goodsDetail.goodsPrice());
        assertEquals(goods.getQuantity(), goodsDetail.quantity());
        assertEquals(0, goodsDetail.tradeList().size());
        verify(goodsRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("상품 상세 조회 실패 - 판매자가 탈퇴한 경우")
    void fail_SellerWithdrawal() {
        // 현재 고려사항이 아님
    }

    @Test
    @DisplayName("상품 상세 조회 실패 - 상품이 존재하지 않음")
    void fail_NoGoods() {
        // given

        // mocking
        given(goodsRepository.findById(anyLong())).willReturn(Optional.empty());
        // when
        // then
        GoodsNotFoundException exception =
                assertThrows(GoodsNotFoundException.class, () -> {
            goodsService.getGoodsDetail(1L, 2L);
        });
        assertEquals("해당 상품이 존재하지 않습니다.", exception.getMessage());
        verify(goodsRepository, times(1)).findById(anyLong());
    }

}

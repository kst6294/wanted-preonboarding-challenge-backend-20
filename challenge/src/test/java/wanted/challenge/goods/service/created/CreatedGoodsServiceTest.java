package wanted.challenge.goods.service.created;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wanted.challenge.goods.entity.Goods;
import wanted.challenge.goods.service.GoodsServiceTest;
import wanted.challenge.mypage.entity.Member;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CreatedGoodsServiceTest extends GoodsServiceTest {
    @Test
    @DisplayName("상품 생성 성공")
    void success_CreateGoods() {
        // given
        Long sellerId = 1L;
        Long goodsId = 1L;
        Member seller = createTestSeller(sellerId);
        Goods goods = createTestGoods(goodsId, seller);
        // mocking
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(seller));
        given(goodsRepository.save(any(Goods.class))).willReturn(goods);
        // when
        Goods createdGoods = goodsService.createGoods(sellerId, goods);
        // then
        assertNotNull(createdGoods);
        assertEquals(goods.getGoodsName(), createdGoods.getGoodsName());
        verify(memberRepository, times(1)).findById(anyLong());
        verify(goodsRepository, times(1)).save(any(Goods.class));
    }

    @Test
    @DisplayName("상품 생성 실패 -  판매자가 존재하지않음")
    void fail_CreateGoods_NoSeller() {
        // given
        Long sellerId = 1L;
        Long goodsId = 1L;
        Goods goods = createTestGoods(goodsId, createTestSeller(sellerId)) ;
        // mocking
        given(memberRepository.findById(anyLong())).willReturn(Optional.empty());
        // when
        // then
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> {
            goodsService.createGoods(sellerId, goods);
        });
        assertEquals("해당 판매자가 존재하지 않습니다.", exception.getMessage());
        verify(memberRepository, times(1)).findById(anyLong());
        verify(goodsRepository, times(0)).save(any(Goods.class));
    }

}

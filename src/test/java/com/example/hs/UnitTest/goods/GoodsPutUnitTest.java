package com.example.hs.UnitTest.goods;

import static com.example.hs.global.exception.ErrorCode.CHECK_GOODS_PRICE_ZERO;
import static com.example.hs.global.exception.ErrorCode.CHECK_IF_SOLD_OUT_GOODS;
import static com.example.hs.global.exception.ErrorCode.NOT_MATCH_SELLER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.hs.domain.goods.dto.GoodsDto;
import com.example.hs.domain.goods.dto.GoodsEditRequest;
import com.example.hs.domain.goods.type.GoodsStatus;
import com.example.hs.global.exception.CustomException;
import com.example.hs.global.security.userdetails.CustomUserDetails;
import com.example.hs.global.security.userdetails.MemberUserDetailsDomain;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GoodsPutUnitTest extends GoodsBaseUnitTest {
  @DisplayName("상품 수정 성공")
  @Test
  public void testUpdateGoods_success() {
    GoodsEditRequest goodsEditRequest = new GoodsEditRequest(
        "상품1", "상품1에대한 설명이 수정되었습니다", 1110, 3, GoodsStatus.SALE
    );
    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockSeller));

    when(memberRepository.findById(1L)).thenReturn(Optional.of(mockSeller));
    when(goodsRepository.findByIdWithLock(1L)).thenReturn(Optional.of(mockGoods));

    GoodsDto goodsDto = goodsService.updateGoods(1L, goodsEditRequest, memberDetails);

    assertNotNull(goodsDto);
    assertEquals("상품1", goodsDto.getGoodsName());
    assertEquals("상품1에대한 설명이 수정되었습니다", goodsDto.getDescription());
    assertEquals(1110, goodsDto.getPrice());
    verify(goodsRepository, times(1)).findByIdWithLock(1L);
  }

  @DisplayName("상품 수정 실패 - 판매자 불일치")
  @Test
  public void testUpdateGoods_fail_notMatchSeller() {
    GoodsEditRequest goodsEditRequest = new GoodsEditRequest(
        "상품1", "상품1에대한 설명이 수정되었습니다", 1110, 3, GoodsStatus.SALE
    );

    CustomUserDetails anotherMemberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockBuyer));

    when(memberRepository.findById(2L)).thenReturn(Optional.of(mockBuyer));
    when(goodsRepository.findByIdWithLock(1L)).thenReturn(Optional.of(mockGoods));

    CustomException exception = assertThrows(CustomException.class, () -> {
      goodsService.updateGoods(1L, goodsEditRequest, anotherMemberDetails);
    });

    assertEquals(NOT_MATCH_SELLER, exception.getErrorCode());
    verify(goodsRepository, times(1)).findByIdWithLock(1L);
  }

  @DisplayName("상품 수정 실패 - 수량이 0인데 SALE로 수정하는 경우")
  @Test
  public void testUpdateGoods_fail_notMatchSaleAndQuantity() {
    GoodsEditRequest goodsEditRequest = new GoodsEditRequest(
        "상품1", "상품1에대한 설명이 수정되었습니다", 1110, 0, GoodsStatus.SALE
    );

    CustomUserDetails anotherMemberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockBuyer));
    when(goodsRepository.findByIdWithLock(1L)).thenReturn(Optional.of(mockGoods));

    CustomException exception = assertThrows(CustomException.class, () -> {
      goodsService.updateGoods(1L, goodsEditRequest, anotherMemberDetails);
    });

    assertEquals(CHECK_IF_SOLD_OUT_GOODS, exception.getErrorCode());
    verify(goodsRepository, times(1)).findByIdWithLock(1L);
  }

  @DisplayName("상품 수정 실패 - 가격이 0")
  @Test
  public void testUpdateGoods_fail_zeroPrice() {
    GoodsEditRequest goodsEditRequest = new GoodsEditRequest(
        "상품1", "상품1에대한 설명이 수정되었습니다", 0, 3, GoodsStatus.SALE
    );

    CustomUserDetails anotherMemberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockBuyer));
    when(goodsRepository.findByIdWithLock(1L)).thenReturn(Optional.of(mockGoods));

    CustomException exception = assertThrows(CustomException.class, () -> {
      goodsService.updateGoods(1L, goodsEditRequest, anotherMemberDetails);
    });

    assertEquals(CHECK_GOODS_PRICE_ZERO, exception.getErrorCode());
    verify(goodsRepository, times(1)).findByIdWithLock(1L);
  }
}

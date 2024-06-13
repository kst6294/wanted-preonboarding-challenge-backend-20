package com.example.hs.UnitTest.goods;



import static com.example.hs.global.exception.ErrorCode.CHECK_GOODS_PRICE_ZERO;
import static com.example.hs.global.exception.ErrorCode.INVALID_GOODS_STATUS_AT_FIRST;
import static com.example.hs.global.exception.ErrorCode.INVALID_MIN_QUANTITY_AT_FIRST;
import static com.example.hs.global.exception.ErrorCode.NOT_FOUND_MEMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.hs.domain.goods.dto.GoodsDto;
import com.example.hs.domain.goods.dto.GoodsRequest;
import com.example.hs.domain.goods.entity.Goods;
import com.example.hs.domain.goods.type.GoodsStatus;
import com.example.hs.global.exception.CustomException;
import com.example.hs.global.security.userdetails.CustomUserDetails;
import com.example.hs.global.security.userdetails.MemberUserDetailsDomain;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GoodsPostUnitTest extends GoodsBaseUnitTest {
  @DisplayName("상품 등록 성공")
  @Test
  public void testPostGoods_success() {
    GoodsRequest goodsRequest = new GoodsRequest(
        "상품1","상품1에대한 설명", 1000, 2, GoodsStatus.SALE);

    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockSeller));

    when(memberRepository.findById(1L)).thenReturn(Optional.of(mockSeller));
    when(goodsRepository.save(any(Goods.class))).thenReturn(mockGoods);

    GoodsDto goodsDto = goodsService.postGoods(goodsRequest, memberDetails);

    assertNotNull(goodsDto);
    assertEquals("상품1", goodsDto.getGoodsName());
    verify(goodsRepository, times(1)).save(any(Goods.class));
  }

  @DisplayName("상품 등록 실패 - 수량 0")
  @Test
  public void testPostGoods_fail_zeroQuantity() {
    GoodsRequest goodsRequest = new GoodsRequest(
        "상품1","상품1에대한 설명", 1000, 0, GoodsStatus.SALE);

    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockSeller));

    CustomException exception = assertThrows(CustomException.class, () -> {
      goodsService.postGoods(goodsRequest, memberDetails);
    });

    assertEquals(INVALID_MIN_QUANTITY_AT_FIRST, exception.getErrorCode());
    verify(goodsRepository, never()).save(any(Goods.class));
  }

  @DisplayName("상품 등록 실패 - 가격이 0")
  @Test
  public void testPostGoods_fail_zeroPrice() {
    GoodsRequest goodsRequest = new GoodsRequest(
        "상품1","상품1에대한 설명", 0, 2, GoodsStatus.SALE);

    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockSeller));

    CustomException exception = assertThrows(CustomException.class, () -> {
      goodsService.postGoods(goodsRequest, memberDetails);
    });

    assertEquals(CHECK_GOODS_PRICE_ZERO, exception.getErrorCode());
    verify(goodsRepository, never()).save(any(Goods.class));
  }

  @DisplayName("상품 등록 실패 - 잘못된 상태: 예약")
  @Test
  public void testPostGoods_fail_invalidStatus_reserved() {
    GoodsRequest goodsRequest = new GoodsRequest(
        "상품1","상품1에대한 설명", 1000, 2, GoodsStatus.RESERVED);

    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockSeller));

    CustomException exception = assertThrows(CustomException.class, () -> {
      goodsService.postGoods(goodsRequest, memberDetails);
    });

    assertEquals(INVALID_GOODS_STATUS_AT_FIRST, exception.getErrorCode());
    verify(goodsRepository, never()).save(any(Goods.class));
  }

  @DisplayName("상품 등록 실패 - 잘못된 상태: 판매완료")
  @Test
  public void testPostGoods_fail_invalidStatus_soldOut() {
    GoodsRequest goodsRequest = new GoodsRequest(
        "상품1","상품1에대한 설명", 1000, 2, GoodsStatus.SOLD_OUT);

    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockSeller));

    CustomException exception = assertThrows(CustomException.class, () -> {
      goodsService.postGoods(goodsRequest, memberDetails);
    });

    assertEquals(INVALID_GOODS_STATUS_AT_FIRST, exception.getErrorCode());
    verify(goodsRepository, never()).save(any(Goods.class));
  }

  @DisplayName("상품 등록 실패 - 존재하지 않는 사용자")
  @Test
  public void testPostGoods_fail_invalidMember() {
    GoodsRequest goodsRequest = new GoodsRequest(
        "상품1","상품1에대한 설명", 1000, 2, GoodsStatus.SALE);

    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockSeller));
    when(memberRepository.findById(1L)).thenReturn(Optional.empty());

    CustomException exception = assertThrows(CustomException.class, () -> {
      goodsService.postGoods(goodsRequest, memberDetails);
    });

    assertEquals(NOT_FOUND_MEMBER, exception.getErrorCode());
    verify(goodsRepository, never()).save(any(Goods.class));
  }
}

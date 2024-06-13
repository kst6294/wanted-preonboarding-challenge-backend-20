package com.example.hs.UnitTest.goods;

import static com.example.hs.global.exception.ErrorCode.NOT_FOUND_MEMBER;
import static com.example.hs.global.exception.ErrorCode.NOT_MATCH_SELLER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.hs.domain.goods.dto.GoodsDto;
import com.example.hs.domain.goods.dto.GoodsEditRequest;
import com.example.hs.domain.goods.entity.Goods;
import com.example.hs.domain.goods.type.GoodsStatus;
import com.example.hs.global.exception.CustomException;
import com.example.hs.global.security.userdetails.CustomUserDetails;
import com.example.hs.global.security.userdetails.MemberUserDetailsDomain;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GoodsDeleteUnitTest extends GoodsBaseUnitTest {
  @DisplayName("상품 삭제 성공")
  @Test
  public void testDeleteGoods_success() {
    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockSeller));

    when(goodsRepository.findById(1L)).thenReturn(Optional.of(mockGoods));
    when(memberRepository.findById((1L))).thenReturn(Optional.of(mockSeller));

    String message = goodsService.deleteGoods(1L, memberDetails);

    assertNotNull(message);
    assertEquals("상품1 상품이 삭제되었습니다.", message);
    verify(goodsRepository, times(1)).delete(any(Goods.class));
  }

  @DisplayName("상품 삭제 실패 - 판매자 불일치")
  @Test
  public void testDeleteGoods_fail_notMatchSeller() {
    CustomUserDetails anotherMemberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockBuyer));

    when(goodsRepository.findById(1L)).thenReturn(Optional.of(mockGoods));
    when(memberRepository.findById((2L))).thenReturn(Optional.of(mockBuyer));

    CustomException exception = assertThrows(CustomException.class, () -> {
      goodsService.deleteGoods(1L, anotherMemberDetails);
    });

    assertEquals(NOT_MATCH_SELLER, exception.getErrorCode());
    verify(goodsRepository, never()).delete(any(Goods.class));
  }

  @DisplayName("상품 삭제 실패 - 존재하지 않는 사용자")
  @Test
  public void testDeleteGoods_fail_notMember() {
    CustomUserDetails anotherMemberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockSeller));

    when(goodsRepository.findById(1L)).thenReturn(Optional.of(mockGoods));
    when(memberRepository.findById((1L))).thenReturn(Optional.empty());

    CustomException exception = assertThrows(CustomException.class, () -> {
      goodsService.deleteGoods(1L, anotherMemberDetails);
    });

    assertEquals(NOT_FOUND_MEMBER, exception.getErrorCode());
    verify(goodsRepository, never()).delete(any(Goods.class));
  }
}

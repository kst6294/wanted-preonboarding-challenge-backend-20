package com.example.hs.UnitTest.goods;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.hs.domain.goods.dto.GoodsDto;
import com.example.hs.domain.goods.dto.GoodsDtoForBuyer;
import com.example.hs.domain.goods.dto.GoodsDtoForSeller;
import com.example.hs.domain.goods.type.GoodsStatus;
import com.example.hs.domain.transaction.entity.Transaction;
import com.example.hs.domain.transaction.type.TransactionStatus;
import com.example.hs.global.security.userdetails.CustomUserDetails;
import com.example.hs.global.security.userdetails.MemberUserDetailsDomain;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GoodsGetUnitTest extends GoodsBaseUnitTest {

  @DisplayName("모든 상품 가져오기 > 비로그인/로그인: 상품이 1개 있을 때")
  @Test
  public void testGetAllWhenGoodsOneByNon_Login() {
    when(goodsRepository.findAll()).thenReturn(Collections.singletonList(mockGoods));

    List<GoodsDto> goodsList = goodsService.getAll();

    assertNotNull(goodsList);
    assertEquals(1, goodsList.size());
    verify(goodsRepository, times(1)).findAll();
  }

  @DisplayName("모든 상품 가져오기 > 비로그인/로그인: 상품이 없을 때")
  @Test
  public void testGetAllWhenNoGoodsByNon_Login() {
//    when(goodsRepository.findAll()).thenReturn(null); // null이여도 NullException 반환하지 않음.
    when(goodsRepository.findAll()).thenReturn(Collections.emptyList());

    List<GoodsDto> goodsList = goodsService.getAll();

    assertNotNull(goodsList);
    assertEquals(0, goodsList.size());
    verify(goodsRepository, times(1)).findAll();
  }

  @DisplayName("특정 상품 가져오기 > 비로그인")
  @Test
  public void testGetGoodsDetailWhenGoodsOneByNon_Login() {
    when(goodsRepository.findByIdWithLock(1L)).thenReturn(Optional.of(mockGoods));
//    GoodsDto goodsDto = goodsService.getGoodsDetail(1L, null); null 자체를 넣어도
    //  CustomUserDetail안에 MemberUserDetailsDomain이 null이여도 Exception 안나도록 수정
    CustomUserDetails customUserDetails = new CustomUserDetails(null);

    GoodsDto goodsDto = goodsService.getGoodsDetail(1L, customUserDetails);

    assertNotNull(goodsDto);
    assertEquals("상품1", goodsDto.getGoodsName());
    assertEquals("상품1에대한 설명", goodsDto.getDescription());
    assertEquals(GoodsStatus.SALE, goodsDto.getGoodsStatus());
    assertEquals(1000, goodsDto.getPrice());

    // 판매자와 구매자일 경우는 확인할 수 없다.
    assertThrows(ClassCastException.class, () -> {
      GoodsDtoForSeller sellerDto = (GoodsDtoForSeller) goodsDto;
    });

    assertThrows(ClassCastException.class, () -> {
      GoodsDtoForBuyer buyerDto = (GoodsDtoForBuyer) goodsDto;
    });

    verify(goodsRepository, times(1)).findByIdWithLock(1L);
  }

  @DisplayName("특정 상품 가져오기 > 로그인한 판매자, 거래 내역이 없을 경우")
  @Test
  public void testGetGoodsDetailWhenGoodsOneByLoginSeller_NoTransaction() {
    when(goodsRepository.findByIdWithLock(1L)).thenReturn(Optional.of(mockGoods));
    CustomUserDetails customUserDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockSeller));
    when(transactionRepository.findAllByGoods(mockGoods)).thenReturn(Collections.emptyList());

    GoodsDto goodsDto = goodsService.getGoodsDetail(1L, customUserDetails);

    assertNotNull(goodsDto);
    assertEquals("상품1", goodsDto.getGoodsName());
    assertEquals("상품1에대한 설명", goodsDto.getDescription());
    assertEquals(GoodsStatus.SALE, goodsDto.getGoodsStatus());
    assertEquals(1000, goodsDto.getPrice());

    // 판매자의 경우로 확인 가능
    GoodsDtoForSeller sellerDto = (GoodsDtoForSeller) goodsDto;
    assertEquals(0, sellerDto.getReservedQuantity());
    assertEquals(0, sellerDto.getTotalTransactionQuantity());
    assertEquals(0, sellerDto.getTransactions().size());

    // 구매자일 경우는 확인할 수 없다.
    assertThrows(ClassCastException.class, () -> {
      GoodsDtoForBuyer buyerDto = (GoodsDtoForBuyer) goodsDto;
    });

    verify(goodsRepository, times(1)).findByIdWithLock(1L);
    verify(transactionRepository, times(1)).findAllByGoods(mockGoods);
  }

  @DisplayName("특정 상품 가져오기 > 로그인한 구매자, 거래 내역이 없을 경우")
  @Test
  public void testGetGoodsDetailWhenGoodsOneByLoginBuyer_NoTransaction() {
    when(goodsRepository.findByIdWithLock(1L)).thenReturn(Optional.of(mockGoods));
    CustomUserDetails customUserDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockBuyer));
    when(transactionRepository.findByGoodsAndBuyer(mockGoods, mockBuyer)).thenReturn(Optional.empty());
    when(memberRepository.findById(mockBuyer.getId())).thenReturn(Optional.of(mockBuyer));

    GoodsDto goodsDto = goodsService.getGoodsDetail(1L, customUserDetails);

    assertNotNull(goodsDto);
    assertEquals("상품1", goodsDto.getGoodsName());
    assertEquals("상품1에대한 설명", goodsDto.getDescription());
    assertEquals(GoodsStatus.SALE, goodsDto.getGoodsStatus());
    assertEquals(1000, goodsDto.getPrice());

    // 판매자 경우는 확인할 수 없다.
    assertThrows(ClassCastException.class, () -> {
      GoodsDtoForSeller sellerDto = (GoodsDtoForSeller) goodsDto;
    });

    // 구매하지 않았기 때문에 구매자용으로 확인할 수 없다.
    assertThrows(ClassCastException.class, () -> {
      GoodsDtoForBuyer buyerDto = (GoodsDtoForBuyer) goodsDto;
    });

    verify(goodsRepository, times(1)).findByIdWithLock(1L);
    verify(memberRepository, times(1)).findById(mockBuyer.getId());
    verify(transactionRepository, times(1)).findByGoodsAndBuyer(mockGoods, mockBuyer);
  }

  @DisplayName("특정 상품 가져오기 > 로그인한 판매자, 거래 내역이 1개 있을 경우")
  @Test
  public void testGetGoodsDetailWhenGoodsOneByLoginSeller_WithTransaction() {
    when(goodsRepository.findByIdWithLock(1L)).thenReturn(Optional.of(mockGoods));
    CustomUserDetails customUserDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockSeller));
    Transaction mockTransaction = Transaction.builder()
        .id(7L)
        .quantity(1)
        .goods(mockGoods)
        .buyer(mockBuyer)
        .priceAtPurchase(1000)
        .transactionStatus(TransactionStatus.RESERVATION)
        .transactionCompleteDateTime(LocalDateTime.now())
        .build();
    when(transactionRepository.findAllByGoods(mockGoods)).thenReturn(Collections.singletonList(mockTransaction));

    GoodsDto goodsDto = goodsService.getGoodsDetail(1L, customUserDetails);

    assertNotNull(goodsDto);
    assertEquals("상품1", goodsDto.getGoodsName());
    assertEquals("상품1에대한 설명", goodsDto.getDescription());
    assertEquals(GoodsStatus.SALE, goodsDto.getGoodsStatus());
    assertEquals(1000, goodsDto.getPrice());

    // 판매자의 경우로 확인 가능
    GoodsDtoForSeller sellerDto = (GoodsDtoForSeller) goodsDto;
    assertEquals(0, sellerDto.getReservedQuantity());
    assertEquals(0, sellerDto.getTotalTransactionQuantity());
    assertEquals(1, sellerDto.getTransactions().size());

    // 구매자일 경우는 확인할 수 없다.
    assertThrows(ClassCastException.class, () -> {
      GoodsDtoForBuyer buyerDto = (GoodsDtoForBuyer) goodsDto;
    });

    verify(goodsRepository, times(1)).findByIdWithLock(1L);
    verify(transactionRepository, times(1)).findAllByGoods(mockGoods);
  }

  @DisplayName("특정 상품 가져오기 > 로그인한 구매자, 거래 내역이 있을 경우")
  @Test
  public void testGetGoodsDetailWhenGoodsOneByLoginBuyer_WithTransaction() {

    when(goodsRepository.findByIdWithLock(1L)).thenReturn(Optional.of(mockGoods));
    CustomUserDetails customUserDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockBuyer));
    when(memberRepository.findById(mockBuyer.getId())).thenReturn(Optional.of(mockBuyer));
    Transaction mockTransaction = Transaction.builder()
        .id(7L)
        .quantity(1)
        .goods(mockGoods)
        .buyer(mockBuyer)
        .priceAtPurchase(1000)
        .transactionStatus(TransactionStatus.RESERVATION)
        .transactionCompleteDateTime(LocalDateTime.now())
        .build();
    when(transactionRepository.findByGoodsAndBuyer(mockGoods, mockBuyer)).thenReturn(Optional.of(mockTransaction));


    GoodsDto goodsDto = goodsService.getGoodsDetail(1L, customUserDetails);

    assertNotNull(goodsDto);
    assertEquals("상품1", goodsDto.getGoodsName());
    assertEquals("상품1에대한 설명", goodsDto.getDescription());
    assertEquals(GoodsStatus.SALE, goodsDto.getGoodsStatus());
    assertEquals(1000, goodsDto.getPrice());

    // 판매자 경우는 확인할 수 없다.
    assertThrows(ClassCastException.class, () -> {
      GoodsDtoForSeller sellerDto = (GoodsDtoForSeller) goodsDto;
    });

    // 구매했기 때문에 구매자용으로 확인할 수 있다.
    GoodsDtoForBuyer buyerDto = (GoodsDtoForBuyer) goodsDto;

    assertEquals(2L, buyerDto.getTransaction().getBuyerId());
    assertEquals(1, buyerDto.getTransaction().getQuantity());

    verify(goodsRepository, times(1)).findByIdWithLock  (1L);
    verify(memberRepository, times(1)).findById(mockBuyer.getId());
    verify(transactionRepository, times(1)).findByGoodsAndBuyer(mockGoods, mockBuyer);
  }
}

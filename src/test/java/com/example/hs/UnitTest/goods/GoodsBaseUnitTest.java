package com.example.hs.UnitTest.goods;

import com.example.hs.domain.auth.entity.Member;
import com.example.hs.domain.auth.repository.MemberRepository;
import com.example.hs.domain.auth.type.Authority;
import com.example.hs.domain.goods.entity.Goods;
import com.example.hs.domain.goods.repository.GoodsRepository;
import com.example.hs.domain.goods.service.GoodsService;
import com.example.hs.domain.goods.type.GoodsStatus;
import com.example.hs.domain.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class GoodsBaseUnitTest {

  @Mock
  protected GoodsRepository goodsRepository;
  @Mock
  protected MemberRepository memberRepository;
  @Mock
  protected TransactionRepository transactionRepository;

  @InjectMocks
  protected GoodsService goodsService;

  protected Goods mockGoods;
  protected Member mockSeller;
  protected Member mockBuyer;

  @BeforeEach
  public void setUp() {
    mockSeller = Member.builder()
        .id(1L)
        .authority(Authority.ROLE_MEMBER)
        .loginId("sellerId@email.com")
        .password("password")
        .name("seller")
        .build();

    mockGoods = Goods.builder()
        .id(1L)
        .goodsName("상품1")
        .description("상품1에대한 설명")
        .totalTransactionQuantity(0)
        .price(1000)
        .availableQuantity(2)
        .reservedQuantity(0)
        .goodsStatus(GoodsStatus.SALE)
        .seller(mockSeller)
        .build();

    mockBuyer = Member.builder()
        .id(2L)
        .authority(Authority.ROLE_MEMBER)
        .loginId("buyer@email.com")
        .password("password")
        .name("buyer")
        .build();
  }
}

package com.example.hs.UnitTest.transactions;

import com.example.hs.domain.auth.entity.Member;
import com.example.hs.domain.auth.repository.MemberRepository;
import com.example.hs.domain.auth.type.Authority;
import com.example.hs.domain.goods.entity.Goods;
import com.example.hs.domain.goods.repository.GoodsRepository;
import com.example.hs.domain.goods.type.GoodsStatus;
import com.example.hs.domain.transaction.entity.Transaction;
import com.example.hs.domain.transaction.repository.TransactionRepository;
import com.example.hs.domain.transaction.service.TransactionService;
import com.example.hs.domain.transaction.type.TransactionStatus;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class TransactionsBaseUnitTest {
  @Mock
  protected TransactionRepository transactionRepository;
  @Mock
  protected GoodsRepository goodsRepository;
  @Mock
  protected MemberRepository memberRepository;
  @InjectMocks
  protected TransactionService transactionService;

  protected Member mockSeller;
  protected Goods mockGoods;
  protected Goods mockSoldOutGoods;
  protected Goods mockReservedGoods;
  protected Member mockBuyer1;
  protected Member mockBuyer2;
  protected Transaction mockTransactionByBuyer1;
  protected Transaction mockTransactionByBuyer2;
  protected Transaction mockTransactionInReservedGoodsStatus;
  protected Transaction mockTransactionInConfirmPurchaseStatus;
  protected Transaction mockTransactionInRefusalStatus;

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

    mockReservedGoods = Goods.builder()
        .id(2L)
        .goodsName("상품2")
        .description("상품2에대한 설명")
        .totalTransactionQuantity(0)
        .price(2000)
        .availableQuantity(1)
        .reservedQuantity(1)
        .goodsStatus(GoodsStatus.RESERVED)
        .seller(mockSeller)
        .build();

    mockSoldOutGoods = Goods.builder()
        .id(3L)
        .goodsName("상품3")
        .description("상품3에대한 설명")
        .totalTransactionQuantity(0)
        .price(3000)
        .availableQuantity(0)
        .reservedQuantity(0)
        .goodsStatus(GoodsStatus.SOLD_OUT)
        .seller(mockSeller)
        .build();

    mockBuyer1 = Member.builder()
        .id(2L)
        .authority(Authority.ROLE_MEMBER)
        .loginId("buyer1@email.com")
        .password("password")
        .name("buyer1")
        .build();

    mockTransactionByBuyer1 = Transaction.builder()
        .id(1L)
        .goods(mockGoods)
        .buyer(mockBuyer1)
        .transactionStatus(TransactionStatus.RESERVATION)
        .priceAtPurchase(1000)
        .quantity(1)
        .transactionCompleteDateTime(LocalDateTime.now())
        .build();

    mockBuyer2 = Member.builder()
        .id(3L)
        .authority(Authority.ROLE_MEMBER)
        .loginId("buyer2@email.com")
        .password("password")
        .name("buyer2")
        .build();

    mockTransactionByBuyer2 = Transaction.builder()
        .id(2L)
        .goods(mockGoods)
        .buyer(mockBuyer2)
        .transactionStatus(TransactionStatus.RESERVATION)
        .priceAtPurchase(1000)
        .quantity(1)
        .transactionCompleteDateTime(LocalDateTime.now())
        .build();

    mockTransactionInReservedGoodsStatus = Transaction.builder()
        .id(3L)
        .goods(mockReservedGoods)
        .buyer(mockBuyer2)
        .transactionStatus(TransactionStatus.RESERVATION)
        .priceAtPurchase(1000)
        .quantity(1)
        .transactionCompleteDateTime(LocalDateTime.now())
        .build();

    mockTransactionInConfirmPurchaseStatus = Transaction.builder()
        .id(4L)
        .goods(mockGoods)
        .buyer(mockBuyer2)
        .transactionStatus(TransactionStatus.CONFIRM_PURCHASE)
        .priceAtPurchase(1000)
        .quantity(1)
        .transactionCompleteDateTime(LocalDateTime.now())
        .build();

    mockTransactionInRefusalStatus = Transaction.builder()
        .id(5L)
        .goods(mockGoods)
        .buyer(mockBuyer2)
        .transactionStatus(TransactionStatus.REFUSAL_OF_SALE)
        .priceAtPurchase(1000)
        .quantity(1)
        .transactionCompleteDateTime(LocalDateTime.now())
        .build();
  }
}

package kr.co.wanted.market.trade.service;

import kr.co.wanted.market.TestUtil;
import kr.co.wanted.market.common.global.dto.PageInfo;
import kr.co.wanted.market.common.global.dto.PageResult;
import kr.co.wanted.market.common.global.enums.ErrorCode;
import kr.co.wanted.market.common.global.error.BizException;
import kr.co.wanted.market.member.entity.Member;
import kr.co.wanted.market.member.repository.MemberRepository;
import kr.co.wanted.market.product.entity.Product;
import kr.co.wanted.market.product.enums.ProductState;
import kr.co.wanted.market.product.repository.ProductRepository;
import kr.co.wanted.market.trade.dto.Purchase;
import kr.co.wanted.market.trade.dto.TradeHistory;
import kr.co.wanted.market.trade.dto.TradeStateModification;
import kr.co.wanted.market.trade.enums.TradeState;
import kr.co.wanted.market.trade.repository.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class TradeServiceTest {

    @Autowired
    TradeService tradeService;
    @Autowired
    TradeRepository tradeRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    PlatformTransactionManager transactionManager;


    @Test
    @DirtiesContext
    @DisplayName("동시구매 테스트")
    void concurrent_purchase_test() throws InterruptedException {

        int threads = 10;

        // given - 판매자 및 구매자 등록
        Purchase.PurchaseBuilder purchaseBuilder = Purchase.builder();

        List<Member> members = TestUtil.getMemberArbitraryBuilder()
                .sampleList(threads + 1);

        TestUtil.getTransaction(transactionManager, TransactionDefinition.PROPAGATION_REQUIRES_NEW)
                .execute(status -> {

                    memberRepository.saveAll(members);

                    Member seller = members.removeFirst();
                    TestUtil.setContextMember(seller.getId(), seller.getRole());

                    // 상품 1개 등록
                    Product product = TestUtil.getProductArbitraryBuilder()
                            .set("seller", seller)
                            .set("quantity", 1L)
                            .sample();
                    productRepository.save(product);

                    // 생성된 ID 저장
                    purchaseBuilder.productId(product.getId());

                    return null;
                });

        // when - 동시구매
        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(threads);

        Purchase purchase = purchaseBuilder.build();
        members.forEach(m -> executorService.submit(
                        () -> {
                            TestUtil.setContextMember(m.getId(), m.getRole());
                            try {
                                startLatch.await();

                                // 구매하기
                                tradeService.purchase(purchase);

                            } catch (Exception e) {
                                log.warn("{}", e.getMessage(), e);
                            } finally {
                                endLatch.countDown();
                            }
                        }
                )
        );

        startLatch.countDown();
        endLatch.await();
        executorService.close();

        // then
        Product product = productRepository.findById(purchase.productId()).get();

        // 재고가 0 이어야 한다.
        assertEquals(0, product.getQuantity());
        // 재고가 0이면 예약중 상태로 변해야 한다.
        assertEquals(ProductState.BOOKING, product.getState());
        // 거래는 1개만 생성되어야 한다.
        assertEquals(1, tradeRepository.count());
    }


    @Test
    @DisplayName("본인제품 구매불가")
    void my_product_purchase_impossible() {

        // given
        Member member = memberRepository.save(
                TestUtil.getMemberArbitraryBuilder()
                        .sample()
        );

        TestUtil.setContextMember(member.getId(), member.getRole());

        Product product = productRepository.save(
                TestUtil.getProductArbitraryBuilder()
                        .set("seller", member)
                        .sample()
        );

        // when then
        BizException e = assertThrows(
                BizException.class,

                () -> tradeService.purchase(
                        Purchase.builder()
                                .productId(product.getId())
                                .build())
        );
        assertEquals(ErrorCode.TRADE_NOT_PROCESSED, e.getErrorCode());
    }


    @ParameterizedTest(name = "[{index}] total: {0}, page: {1}, size: {2}")
    @DisplayName("판매거래 페이징 검색")
    @CsvSource(
            delimiter = '|',
            // totalCount|page|size
            value = {"17|0|10", "17|1|10", "9|0|10", "9|1|5"})
    void sale_trades_paging_searching(int totalCount, int page, int size) {

        // given
        List<Member> members = memberRepository.saveAll(
                TestUtil.getMemberArbitraryBuilder()
                        .sampleList(totalCount + 1)
        );

        // 판매자
        Member seller = members.removeFirst();
        TestUtil.setContextMember(seller.getId(), seller.getRole());

        // 상품생성
        Product product = productRepository.save(
                TestUtil.getProductArbitraryBuilder()
                        .set("seller", seller)
                        .set("quantity", (long) totalCount)
                        .sample()
        );

        // 거래생성
        members.forEach(m -> {
                    TestUtil.setContextMember(m.getId(), m.getRole());
                    tradeService.purchase(Purchase.builder()
                            .productId(product.getId())
                            .build());
                }
        );

        // when
        TestUtil.setContextMember(seller.getId(), seller.getRole());
        PageResult<TradeHistory> result = tradeService.searchSales(new PageInfo(page, size));

        // then
        assertEquals(
                totalCount >= (page + 1) * size ? size : totalCount % size,
                result.list().size());
        assertEquals(page, result.page());
        assertEquals(size, result.size());
        assertEquals(Math.ceilDiv(totalCount, size), result.totalPages());
        assertEquals(page == 0, result.first());
        assertEquals(page + 1 >= Math.ceilDiv(totalCount, size), result.last());
    }


    @ParameterizedTest(name = "[{index}] total: {0}, page: {1}, size: {2}")
    @DisplayName("구매거래 페이징 검색")
    @CsvSource(
            delimiter = '|',
            // totalCount|page|size
            value = {"17|0|10", "17|1|10", "9|0|10", "9|1|5"})
    void purchase_trades_paging_searching(int totalCount, int page, int size) {

        // given
        List<Member> members = memberRepository.saveAll(
                TestUtil.getMemberArbitraryBuilder()
                        .sampleList(2)
        );

        // 판매자
        Member seller = members.getFirst();
        TestUtil.setContextMember(seller.getId(), seller.getRole());

        // 상품생성
        List<Product> products = productRepository.saveAll(
                TestUtil.getProductArbitraryBuilder()
                        .set("seller", seller)
                        .sampleList(totalCount)
        );

        // 구매자
        Member buyer = members.getLast();

        // 거래생성
        TestUtil.setContextMember(buyer.getId(), buyer.getRole());
        products.forEach(p ->
                tradeService.purchase(Purchase.builder()
                        .productId(p.getId())
                        .build())
        );

        // when
        PageResult<TradeHistory> result = tradeService.searchPurchases(new PageInfo(page, size));

        // then
        assertEquals(
                totalCount >= (page + 1) * size ? size : totalCount % size,
                result.list().size());
        assertEquals(page, result.page());
        assertEquals(size, result.size());
        assertEquals(Math.ceilDiv(totalCount, size), result.totalPages());
        assertEquals(page == 0, result.first());
        assertEquals(page + 1 >= Math.ceilDiv(totalCount, size), result.last());
    }


    @Test
    @DisplayName("거래상태변경_성공(예약)")
    void trade_state_modification_booking_success() {

        // given
        List<Member> members = memberRepository.saveAll(
                TestUtil.getMemberArbitraryBuilder()
                        .sampleList(2)
        );

        // 판매자
        Member seller = members.getFirst();
        TestUtil.setContextMember(seller.getId(), seller.getRole());

        // 상품생성
        Product product = productRepository.save(
                TestUtil.getProductArbitraryBuilder()
                        .set("seller", seller)
                        .sample()
        );

        // 구매자
        Member buyer = members.getLast();
        TestUtil.setContextMember(buyer.getId(), buyer.getRole());

        Purchase purchase = tradeService.purchase(Purchase.builder()
                .productId(product.getId())
                .build());

        // when then

        // 판매자가 판매승인하여 예약거래로 변경
        TestUtil.setContextMember(seller.getId(), seller.getRole());
        TradeStateModification changedState = tradeService.changeState(purchase.id(), TradeState.BOOKING);
        assertEquals(purchase.id(), changedState.id());
        assertEquals(TradeState.BOOKING, changedState.state());
    }


    @Test
    @DisplayName("거래상태변경_실패(예약)_판매자아님")
    void trade_state_modification_booking_fail_1() {

        // given
        List<Member> members = memberRepository.saveAll(
                TestUtil.getMemberArbitraryBuilder()
                        .sampleList(2)
        );

        // 판매자
        Member seller = members.getFirst();
        TestUtil.setContextMember(seller.getId(), seller.getRole());

        // 상품생성
        Product product = productRepository.save(
                TestUtil.getProductArbitraryBuilder()
                        .set("seller", seller)
                        .sample()
        );

        // 구매자
        Member buyer = members.getLast();
        TestUtil.setContextMember(buyer.getId(), buyer.getRole());

        Purchase purchase = tradeService.purchase(Purchase.builder()
                .productId(product.getId())
                .build());

        // when then

        // 구매자는 판매승인 불가
        BizException e = assertThrows(
                BizException.class,
                () -> tradeService.changeState(purchase.id(), TradeState.BOOKING));
        assertEquals(ErrorCode.NO_PERMISSION, e.getErrorCode());
    }


    @Test
    @DisplayName("거래상태변경_실패(예약)_상태변경불가")
    void trade_state_modification_booking_fail_2() {

        // given
        List<Member> members = memberRepository.saveAll(
                TestUtil.getMemberArbitraryBuilder()
                        .sampleList(2)
        );

        // 판매자
        Member seller = members.getFirst();
        TestUtil.setContextMember(seller.getId(), seller.getRole());

        // 상품생성
        Product product = productRepository.save(
                TestUtil.getProductArbitraryBuilder()
                        .set("seller", seller)
                        .sample()
        );

        // 구매자
        Member buyer = members.getLast();
        TestUtil.setContextMember(buyer.getId(), buyer.getRole());

        Purchase purchase = tradeService.purchase(Purchase.builder()
                .productId(product.getId())
                .build());

        TestUtil.setContextMember(seller.getId(), seller.getRole());
        tradeService.changeState(purchase.id(), TradeState.BOOKING);

        // when then
        BizException e = assertThrows(
                BizException.class,
                () -> tradeService.changeState(purchase.id(), TradeState.BOOKING));
        assertEquals(ErrorCode.TRADE_STATE_CAN_NOT_CHANGED, e.getErrorCode());
    }


    @Test
    @DisplayName("거래상태변경_성공(확정)")
    void trade_state_modification_confirmation_success() {

        // given
        List<Member> members = memberRepository.saveAll(
                TestUtil.getMemberArbitraryBuilder()
                        .sampleList(2)
        );

        // 판매자
        Member seller = members.getFirst();
        TestUtil.setContextMember(seller.getId(), seller.getRole());

        // 상품생성
        Product product = productRepository.save(
                TestUtil.getProductArbitraryBuilder()
                        .set("seller", seller)
                        .set("quantity", 1L)
                        .sample()
        );

        // 구매자
        Member buyer = members.getLast();
        TestUtil.setContextMember(buyer.getId(), buyer.getRole());

        Purchase purchase = tradeService.purchase(Purchase.builder()
                .productId(product.getId())
                .build());

        // 판매승인
        TestUtil.setContextMember(seller.getId(), seller.getRole());
        tradeService.changeState(purchase.id(), TradeState.BOOKING);

        // when

        // 구매자가 구매확정
        TestUtil.setContextMember(buyer.getId(), buyer.getRole());
        TradeStateModification changedState = tradeService.changeState(purchase.id(), TradeState.CONFIRMATION);

        // then
        assertEquals(purchase.id(), changedState.id());
        assertEquals(TradeState.CONFIRMATION, changedState.state());
        assertEquals(ProductState.COMPLETION, productRepository.findById(product.getId()).get().getState());
    }


    @Test
    @DisplayName("거래상태변경_실패(확정)_구매자아님")
    void trade_state_modification_confirmation_fail_1() {

        // given
        List<Member> members = memberRepository.saveAll(
                TestUtil.getMemberArbitraryBuilder()
                        .sampleList(2)
        );

        // 판매자
        Member seller = members.getFirst();
        TestUtil.setContextMember(seller.getId(), seller.getRole());

        // 상품생성
        Product product = productRepository.save(
                TestUtil.getProductArbitraryBuilder()
                        .set("seller", seller)
                        .set("quantity", 1L)
                        .sample()
        );

        // 구매자
        Member buyer = members.getLast();
        TestUtil.setContextMember(buyer.getId(), buyer.getRole());

        Purchase purchase = tradeService.purchase(Purchase.builder()
                .productId(product.getId())
                .build());

        // 판매승인
        TestUtil.setContextMember(seller.getId(), seller.getRole());
        tradeService.changeState(purchase.id(), TradeState.BOOKING);

        // when then

        // 판매자가 구매확정
        BizException e = assertThrows(
                BizException.class,
                () -> tradeService.changeState(purchase.id(), TradeState.CONFIRMATION));
        assertEquals(ErrorCode.NO_PERMISSION, e.getErrorCode());
    }


    @Test
    @DisplayName("거래상태변경_실패(확정)_상태변경불가")
    void trade_state_modification_confirmation_fail_2() {

        // given
        List<Member> members = memberRepository.saveAll(
                TestUtil.getMemberArbitraryBuilder()
                        .sampleList(2)
        );

        // 판매자
        Member seller = members.getFirst();
        TestUtil.setContextMember(seller.getId(), seller.getRole());

        // 상품생성
        Product product = productRepository.save(
                TestUtil.getProductArbitraryBuilder()
                        .set("seller", seller)
                        .set("quantity", 1L)
                        .sample()
        );

        // 구매자
        Member buyer = members.getLast();
        TestUtil.setContextMember(buyer.getId(), buyer.getRole());

        Purchase purchase = tradeService.purchase(Purchase.builder()
                .productId(product.getId())
                .build());

        // when then
        BizException e = assertThrows(
                BizException.class,
                () -> tradeService.changeState(purchase.id(), TradeState.CONFIRMATION));
        assertEquals(ErrorCode.TRADE_STATE_CAN_NOT_CHANGED, e.getErrorCode());
    }


    @Test
    @DisplayName("거래상태변경_성공(취소)_판매자")
    void trade_state_modification_cancel_success_seller() {

        // given
        List<Member> members = memberRepository.saveAll(
                TestUtil.getMemberArbitraryBuilder()
                        .sampleList(2)
        );

        // 판매자
        Member seller = members.getFirst();
        TestUtil.setContextMember(seller.getId(), seller.getRole());

        // 상품생성
        Product product = productRepository.save(
                TestUtil.getProductArbitraryBuilder()
                        .set("seller", seller)
                        .set("quantity", 1L)
                        .sample()
        );

        // 구매자
        Member buyer = members.getLast();
        TestUtil.setContextMember(buyer.getId(), buyer.getRole());

        Purchase purchase = tradeService.purchase(Purchase.builder()
                .productId(product.getId())
                .build());

        // 판매승인
        TestUtil.setContextMember(seller.getId(), seller.getRole());
        tradeService.changeState(purchase.id(), TradeState.BOOKING);

        // when

        // 판매자가 예약 취소
        TradeStateModification changedState = tradeService.changeState(purchase.id(), TradeState.CANCEL);

        // then
        assertEquals(purchase.id(), changedState.id());
        assertEquals(TradeState.REQUEST, changedState.state());
    }


    @Test
    @DisplayName("거래상태변경_실패(취소)_판매자")
    void trade_state_modification_cancel_fail_seller() {

        // given
        List<Member> members = memberRepository.saveAll(
                TestUtil.getMemberArbitraryBuilder()
                        .sampleList(2)
        );

        // 판매자
        Member seller = members.getFirst();
        TestUtil.setContextMember(seller.getId(), seller.getRole());

        // 상품생성
        Product product = productRepository.save(
                TestUtil.getProductArbitraryBuilder()
                        .set("seller", seller)
                        .set("quantity", 1L)
                        .sample()
        );

        // 구매자
        Member buyer = members.getLast();
        TestUtil.setContextMember(buyer.getId(), buyer.getRole());

        Purchase purchase = tradeService.purchase(Purchase.builder()
                .productId(product.getId())
                .build());

        // when then
        TestUtil.setContextMember(seller.getId(), seller.getRole());
        BizException e = assertThrows(
                BizException.class,
                () -> tradeService.changeState(purchase.id(), TradeState.CANCEL));
        assertEquals(ErrorCode.TRADE_STATE_CAN_NOT_CHANGED, e.getErrorCode());
    }


    @Test
    @DisplayName("거래상태변경_성공(취소)_구매자")
    void trade_state_modification_cancel_success_buyer() {

        // given
        List<Member> members = memberRepository.saveAll(
                TestUtil.getMemberArbitraryBuilder()
                        .sampleList(2)
        );

        // 판매자
        Member seller = members.getFirst();
        TestUtil.setContextMember(seller.getId(), seller.getRole());

        // 상품생성
        Product product = productRepository.save(
                TestUtil.getProductArbitraryBuilder()
                        .set("seller", seller)
                        .set("quantity", 1L)
                        .sample()
        );

        // 구매자
        Member buyer = members.getLast();
        TestUtil.setContextMember(buyer.getId(), buyer.getRole());

        Purchase purchase = tradeService.purchase(Purchase.builder()
                .productId(product.getId())
                .build());

        // when

        // 구매취소
        TradeStateModification changedState = tradeService.changeState(purchase.id(), TradeState.CANCEL);

        // then
        assertEquals(purchase.id(), changedState.id());
        assertEquals(TradeState.CANCEL, changedState.state());
        assertEquals(1L, productRepository.findById(product.getId()).get().getQuantity());
    }


    @Test
    @DisplayName("거래상태변경_실패(취소)_구매자")
    void trade_state_modification_cancel_fail_buyer() {

        // given
        List<Member> members = memberRepository.saveAll(
                TestUtil.getMemberArbitraryBuilder()
                        .sampleList(2)
        );

        // 판매자
        Member seller = members.getFirst();
        TestUtil.setContextMember(seller.getId(), seller.getRole());

        // 상품생성
        Product product = productRepository.save(
                TestUtil.getProductArbitraryBuilder()
                        .set("seller", seller)
                        .set("quantity", 1L)
                        .sample()
        );

        // 구매자
        Member buyer = members.getLast();
        TestUtil.setContextMember(buyer.getId(), buyer.getRole());

        Purchase purchase = tradeService.purchase(Purchase.builder()
                .productId(product.getId())
                .build());

        // 판매승인
        TestUtil.setContextMember(seller.getId(), seller.getRole());
        tradeService.changeState(purchase.id(), TradeState.BOOKING);

        // when then
        TestUtil.setContextMember(buyer.getId(), buyer.getRole());
        BizException e = assertThrows(
                BizException.class,
                () -> tradeService.changeState(purchase.id(), TradeState.CANCEL));
        assertEquals(ErrorCode.TRADE_STATE_CAN_NOT_CHANGED, e.getErrorCode());
    }
}
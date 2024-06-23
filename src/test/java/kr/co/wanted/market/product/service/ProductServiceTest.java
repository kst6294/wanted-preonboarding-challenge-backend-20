package kr.co.wanted.market.product.service;

import kr.co.wanted.market.TestUtil;
import kr.co.wanted.market.common.global.dto.PageInfo;
import kr.co.wanted.market.common.global.dto.PageResult;
import kr.co.wanted.market.member.entity.Member;
import kr.co.wanted.market.member.repository.MemberRepository;
import kr.co.wanted.market.product.dto.ProductDetail;
import kr.co.wanted.market.product.dto.ProductRegistration;
import kr.co.wanted.market.product.dto.ProductSimple;
import kr.co.wanted.market.product.entity.Product;
import kr.co.wanted.market.product.repository.ProductRepository;
import kr.co.wanted.market.trade.dto.Purchase;
import kr.co.wanted.market.trade.service.TradeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ProductServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    TradeService tradeService;

    @Test
    @DisplayName("상품등록 테스트")
    void product_register_test() {

        // given
        Member member = TestUtil.getMemberArbitraryBuilder()
                .sample();
        memberRepository.save(member);

        ProductRegistration registration = ProductRegistration.builder()
                .name("test")
                .price(1_000L)
                .quantity(1L)
                .build();

        // when
        TestUtil.setContextMember(member.getId(), member.getRole());
        ProductRegistration registered = productService.registerProduct(registration);

        // then
        assertNotNull(registered.id());
        assertEquals(member.getId(), registered.sellerId());
        assertEquals(registration.name(), registered.name());
        assertEquals(registration.price(), registered.price());
        assertEquals(registration.quantity(), registered.quantity());
    }


    @Test
    @DisplayName("비로그인 상품상세 조회 테스트")
    void product_detail_non_login_searching() {

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

        // when
        TestUtil.clearContextMember();
        ProductDetail productDetail = productService.searchProductDetail(product.getId());

        // then
        assertEquals(product.getId(), productDetail.id());
        assertEquals(product.getSeller().getId(), productDetail.sellerId());
        assertEquals(product.getName(), productDetail.name());
        assertEquals(product.getPrice(), productDetail.price());
        assertEquals(product.getQuantity(), productDetail.quantity());
        assertEquals(product.getState(), productDetail.state());
        assertNull(productDetail.tradeHistories());
    }


    @Test
    @DisplayName("판매자 상품상세 조회 테스트")
    void product_detail_seller_searching() {

        // given
        List<Member> members = memberRepository.saveAll(
                TestUtil.getMemberArbitraryBuilder()
                        .sampleList(3)
        );

        // 판매자
        Member seller = members.removeFirst();

        TestUtil.setContextMember(seller.getId(), seller.getRole());
        Product product = productRepository.save(
                TestUtil.getProductArbitraryBuilder()
                        .set("seller", seller)
                        .sample()
        );

        // 구매자 구매요청
        members.forEach(buyer -> {
            TestUtil.setContextMember(buyer.getId(), buyer.getRole());
            tradeService.purchase(
                    Purchase.builder()
                            .productId(product.getId())
                            .build()
            );
        });

        // when
        TestUtil.setContextMember(seller.getId(), seller.getRole());
        ProductDetail productDetail = productService.searchProductDetail(product.getId());

        // then
        assertEquals(product.getId(), productDetail.id());
        assertEquals(product.getSeller().getId(), productDetail.sellerId());
        assertEquals(product.getName(), productDetail.name());
        assertEquals(product.getPrice(), productDetail.price());
        assertEquals(product.getQuantity() - members.size(), productDetail.quantity());
        assertEquals(product.getState(), productDetail.state());
        assertEquals(members.size(), productDetail.tradeHistories().size());
        assertEquals(
                members.size(),
                productDetail.tradeHistories()
                        .stream()
                        .filter(th -> Objects.equals(seller.getId(), th.sellerId()))
                        .count()
        );
    }


    @Test
    @DisplayName("구매자 상품상세 조회 테스트")
    void product_detail_buyer_searching() {

        // given
        List<Member> members = memberRepository.saveAll(
                TestUtil.getMemberArbitraryBuilder()
                        .sampleList(2)
        );

        // 판매자 상품등록
        Member seller = members.removeFirst();

        TestUtil.setContextMember(seller.getId(), seller.getRole());
        Product product = productRepository.save(
                TestUtil.getProductArbitraryBuilder()
                        .set("seller", seller)
                        .sample()
        );

        // 구매요청
        Member buyer = members.removeLast();
        TestUtil.setContextMember(buyer.getId(), buyer.getRole());

        tradeService.purchase(
                Purchase.builder()
                        .productId(product.getId())
                        .build()
        );

        // when
        ProductDetail productDetail = productService.searchProductDetail(product.getId());

        // then
        assertEquals(product.getId(), productDetail.id());
        assertEquals(product.getSeller().getId(), productDetail.sellerId());
        assertEquals(product.getName(), productDetail.name());
        assertEquals(product.getPrice(), productDetail.price());
        assertEquals(product.getQuantity() - 1L, productDetail.quantity());
        assertEquals(product.getState(), productDetail.state());
        assertEquals(1L, productDetail.tradeHistories().size());
        assertEquals(
                1L,
                productDetail.tradeHistories()
                        .stream()
                        .filter(th -> Objects.equals(buyer.getId(), th.buyerId()))
                        .count()
        );
    }


    @ParameterizedTest(name = "[{index}] total: {0}, page: {1}, size: {2}")
    @DisplayName("상품목록 페이징 검색")
    @CsvSource(
            delimiter = '|',
            // totalCount|page|size
            value = {"17|0|10", "17|1|10", "9|0|10", "9|1|5"})
    void products_paging_searching(int totalCount, int page, int size) {

        // given
        Member seller = memberRepository.save(
                TestUtil.getMemberArbitraryBuilder()
                        .sample()
        );

        // 상품등록
        TestUtil.setContextMember(seller.getId(), seller.getRole());
        productRepository.saveAll(
                TestUtil.getProductArbitraryBuilder()
                        .set("seller", seller)
                        .sampleList(totalCount)
        );

        // when
        TestUtil.clearContextMember();
        PageResult<ProductSimple> result = productService.searchProducts(new PageInfo(page, size));

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

}
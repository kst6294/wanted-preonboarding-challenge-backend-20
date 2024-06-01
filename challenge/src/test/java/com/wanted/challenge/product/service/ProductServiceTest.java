package com.wanted.challenge.product.service;

import static com.wanted.challenge.transact.model.TransactState.APPROVE;
import static com.wanted.challenge.transact.model.TransactState.DEPOSIT;

import com.wanted.challenge.IntegrationTestSupport;
import com.wanted.challenge.account.entity.Account;
import com.wanted.challenge.account.repository.AccountRepository;
import com.wanted.challenge.exception.ExceptionStatus;
import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.product.model.Price;
import com.wanted.challenge.product.model.Quantity;
import com.wanted.challenge.product.model.Reservation;
import com.wanted.challenge.product.repository.ProductRepository;
import com.wanted.challenge.product.response.ProductDetailResponse;
import com.wanted.challenge.product.response.PurchaseBuyerResponse;
import com.wanted.challenge.product.response.PurchaseDetailResponse;
import com.wanted.challenge.product.response.PurchaseProductResponse;
import com.wanted.challenge.transact.entity.Transact;
import com.wanted.challenge.transact.entity.TransactLog;
import com.wanted.challenge.transact.repository.TransactLogRepository;
import com.wanted.challenge.transact.repository.TransactRepository;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

class ProductServiceTest extends IntegrationTestSupport {

    @Autowired
    ProductService productService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    TransactRepository transactRepository;

    @Autowired
    TransactLogRepository transactLogRepository;

    Account buyer1;
    Account buyer2;
    Account seller;
    Product product;

    @BeforeEach
    void setUp() {
        buyer1 = accountRepository.save(new Account("buyer1", "1234"));
        buyer2 = accountRepository.save(new Account("buyer2", "1234"));

        seller = accountRepository.save(new Account("seller", "1234"));

        product = productRepository.save(new Product(seller, "상품", new Price(10_000), new Quantity(2)));
    }

    @Test
    @DisplayName("구매자 본인의 거래 내역 확인")
    void purchaseDetailResponses() throws Exception {

        // given
        Transact transact1 = transactRepository.save(new Transact(buyer1, product));
        transactLogRepository.save(new TransactLog(transact1, DEPOSIT));
        transactLogRepository.save(new TransactLog(transact1, APPROVE));

        Transact transact2 = transactRepository.save(new Transact(buyer2, product));
        transactLogRepository.save(new TransactLog(transact2, DEPOSIT));

        // when
        ProductDetailResponse productDetailResponse =
                productService.detail(product.getId(), Optional.of(buyer1.getId()));

        // then
        Assertions.assertThat(productDetailResponse)
                .extracting(ProductDetailResponse::productName, ProductDetailResponse::purchaseInfos)
                .containsExactly("상품",
                        List.of(new PurchaseDetailResponse(DEPOSIT), new PurchaseDetailResponse(APPROVE)));
    }

    @Test
    @DisplayName("판매자의 구매자들 거래 내역 확인")
    void purchaseBuyerResponses() throws Exception {

        // given
        Transact transact1 = transactRepository.save(new Transact(buyer1, product));
        transactLogRepository.save(new TransactLog(transact1, DEPOSIT));
        transactLogRepository.save(new TransactLog(transact1, APPROVE));

        Transact transact2 = transactRepository.save(new Transact(buyer2, product));
        transactLogRepository.save(new TransactLog(transact2, DEPOSIT));

        // when
        ProductDetailResponse productDetailResponse =
                productService.detail(product.getId(), Optional.of(seller.getId()));

        // then
        Assertions.assertThat(productDetailResponse)
                .extracting(ProductDetailResponse::productName, ProductDetailResponse::purchaseInfos)
                .containsExactly("상품",
                        List.of(new PurchaseBuyerResponse(buyer2.getId(), List.of(DEPOSIT)),
                                new PurchaseBuyerResponse(buyer1.getId(), List.of(APPROVE, DEPOSIT))));
    }

    @Test
    @DisplayName("같은 사용자가 여러 번 구매 불가")
    void purchaseDuplicate() throws Exception {

        // given
        productService.purchase(product.getId(), buyer1.getId());

        // when
        // then
        Assertions.assertThatThrownBy(() -> productService.purchase(product.getId(), buyer1.getId()))
                .hasFieldOrPropertyWithValue("exceptionStatus", ExceptionStatus.PURCHASE_ALREADY);
    }

    @Test
    @DisplayName("모든 제품 구매 시 제품 상태 예약으로 변경")
    void purchaseAll() throws Exception {

        // given
        productService.purchase(product.getId(), buyer1.getId());
        productService.purchase(product.getId(), buyer2.getId());

        // when
        Product afterProduct = productRepository.findById(product.getId())
                .orElseThrow();

        // then
        Assertions.assertThat(afterProduct)
                .extracting(Product::getReservation, Product::getQuantity)
                .containsExactly(Reservation.RESERVE, new Quantity(0));
    }

    @Test
    @DisplayName("본인 제품을 본인이 구매하는 경우 예외가 발생한다")
    void selfBuy() throws Exception {

        // when
        // then
        Assertions.assertThatThrownBy(() -> productService.purchase(product.getId(), seller.getId()))
                .hasFieldOrPropertyWithValue("exceptionStatus", ExceptionStatus.SELF_BUY);
    }

    @Test
    @DisplayName("수량이 0인 제품은 구매할 수 없다")
    void zeroQuantity() throws Exception {

        // given
        Product newProduct = new Product(seller, "newProduct", new Price(100), new Quantity(1));
        newProduct.purchase();

        Product zeroQuantityProduct = productRepository.save(newProduct);

        // when
        // then
        Assertions.assertThatThrownBy(() -> productService.purchase(zeroQuantityProduct.getId(), buyer1.getId()))
                .hasFieldOrPropertyWithValue("exceptionStatus", ExceptionStatus.CAN_NOT_PURCHASE);
    }

    @Test
    @DisplayName("거래 기록 조회 시 구매 당시의 가격 정보가 나타나야 한다")
    void purchasePrice() throws Exception {

        // given
        transactRepository.save(new Transact(buyer1, product));
        productService.updatePrice(product.getId(), 20_000, seller.getId());

        // when
        Page<PurchaseProductResponse> purchaseProductResponses =
                productService.purchaseProducts(buyer1.getId(), PageRequest.of(0, 20));

        // then
        Assertions.assertThat(purchaseProductResponses)
                .extracting(PurchaseProductResponse::price)
                .containsExactly(10_000);
    }
}

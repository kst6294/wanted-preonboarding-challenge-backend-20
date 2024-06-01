package com.wanted.challenge.product.repository;

import static com.wanted.challenge.transact.model.TransactState.APPROVE;
import static com.wanted.challenge.transact.model.TransactState.DEPOSIT;
import static org.assertj.core.groups.Tuple.tuple;

import com.wanted.challenge.IntegrationTestSupport;
import com.wanted.challenge.account.entity.Account;
import com.wanted.challenge.account.repository.AccountRepository;
import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.product.model.Price;
import com.wanted.challenge.product.model.Quantity;
import com.wanted.challenge.product.model.Reservation;
import com.wanted.challenge.product.response.PurchaseProductResponse;
import com.wanted.challenge.product.response.ReserveProductResponse;
import com.wanted.challenge.transact.entity.Transact;
import com.wanted.challenge.transact.entity.TransactLog;
import com.wanted.challenge.transact.repository.TransactLogRepository;
import com.wanted.challenge.transact.repository.TransactRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

class ProductRepositoryTest extends IntegrationTestSupport {

    @Autowired
    TransactRepository transactRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    TransactLogRepository transactLogRepository;

    @Test
    @DisplayName("구매한 제품과 가장 마지막 거래 내용 확인")
    void retrievePurchaseProducts() throws Exception {

        // given
        Account seller = accountRepository.save(new Account("seller", "password"));
        Account buyer = accountRepository.save(new Account("buyer", "password"));
        Product product1 = productRepository.save(new Product(seller, "product1", new Price(10_000), new Quantity(1)));
        Product product2 = productRepository.save(new Product(seller, "product2", new Price(20_000), new Quantity(1)));

        Transact transact1 = transactRepository.save(new Transact(buyer, product1));
        transactLogRepository.save(new TransactLog(transact1, DEPOSIT));
        transactLogRepository.save(new TransactLog(transact1, APPROVE));

        Transact transact2 = transactRepository.save(new Transact(buyer, product2));
        transactLogRepository.save(new TransactLog(transact2, DEPOSIT));

        // when
        Page<PurchaseProductResponse> purchaseProductResponses =
                productRepository.retrievePurchaseProducts(buyer.getId(), PageRequest.of(0, 10));

        // then
        Assertions.assertThat(purchaseProductResponses)
                .extracting(PurchaseProductResponse::name, PurchaseProductResponse::transactState)
                .containsExactly(tuple("product2", DEPOSIT), tuple("product1", APPROVE));
    }

    @Test
    @DisplayName("예약 제품 목록")
    void retrieveReserveProducts() throws Exception {

        // given
        Account seller = accountRepository.save(new Account("seller", "password"));
        Account buyer = accountRepository.save(new Account("buyer", "password"));

        Product product1 = new Product(seller, "product1", new Price(10_000), new Quantity(1));
        ReflectionTestUtils.setField(product1, "reservation", Reservation.RESERVE);
        productRepository.save(product1);

        Product product2 = productRepository.save(new Product(seller, "product2", new Price(20_000), new Quantity(1)));
        ReflectionTestUtils.setField(product2, "reservation", Reservation.RESERVE);
        productRepository.save(product2);

        Product product3 = productRepository.save(new Product(seller, "product3", new Price(30_000), new Quantity(1)));
        productRepository.save(product3);

        Transact transact1 = transactRepository.save(new Transact(buyer, product1));
        transactLogRepository.save(new TransactLog(transact1, DEPOSIT));

        Transact transact2 = transactRepository.save(new Transact(buyer, product2));
        transactLogRepository.save(new TransactLog(transact2, DEPOSIT));

        // when
        Page<ReserveProductResponse> reserveProductResponses =
                productRepository.retrieveReserveProducts(buyer.getId(), PageRequest.of(0, 10));

        // then
        Assertions.assertThat(reserveProductResponses)
                .extracting(ReserveProductResponse::name)
                .containsExactly("product2", "product1");
    }
}

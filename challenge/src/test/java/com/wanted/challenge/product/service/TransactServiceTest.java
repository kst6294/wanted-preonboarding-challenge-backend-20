package com.wanted.challenge.product.service;

import com.wanted.challenge.IntegrationTestSupport;
import com.wanted.challenge.account.entity.Account;
import com.wanted.challenge.account.repository.AccountRepository;
import com.wanted.challenge.exception.ExceptionStatus;
import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.product.model.Price;
import com.wanted.challenge.product.model.Quantity;
import com.wanted.challenge.product.model.Reservation;
import com.wanted.challenge.product.repository.ProductRepository;
import com.wanted.challenge.transact.entity.Transact;
import com.wanted.challenge.transact.entity.TransactLog;
import com.wanted.challenge.transact.model.TransactState;
import com.wanted.challenge.transact.repository.TransactLogRepository;
import com.wanted.challenge.transact.repository.TransactRepository;
import com.wanted.challenge.transact.service.TransactService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

class TransactServiceTest extends IntegrationTestSupport {

    @Autowired
    TransactService transactService;

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

        transactRepository.save(new Transact(buyer1, product));
        transactRepository.save(new Transact(buyer2, product));
    }

    @Test
    @DisplayName("모든 사용자가 제품 구매 확정 시 제품 상태 완료로 변경")
    void confirmAll() throws Exception {

        // given
        Product newProduct = new Product(seller, "name", new Price(100), new Quantity(0));
        ReflectionTestUtils.setField(newProduct, "reservation", Reservation.RESERVE);

        productRepository.save(newProduct);

        Transact transact1 = transactRepository.save(new Transact(buyer1, newProduct));
        transactLogRepository.save(new TransactLog(transact1, TransactState.DEPOSIT));
        transactLogRepository.save(new TransactLog(transact1, TransactState.APPROVE));

        Transact transact2 = transactRepository.save(new Transact(buyer2, newProduct));
        transactLogRepository.save(new TransactLog(transact2, TransactState.DEPOSIT));
        transactLogRepository.save(new TransactLog(transact2, TransactState.APPROVE));

        // when
        transactService.confirm(newProduct.getId(), buyer1.getId());
        transactService.confirm(newProduct.getId(), buyer2.getId());

        // then
        Product afterProduct = productRepository.findById(newProduct.getId())
                .orElseThrow();

        Assertions.assertThat(afterProduct.getReservation())
                .isEqualByComparingTo(Reservation.COMPLETE);
    }

    @Test
    @DisplayName("판매자가 아닌 사람이 판매승인하는 경우 예외가 발생한다")
    void approveNotSeller() throws Exception {

        // when
        // then
        Assertions.assertThatThrownBy(() -> transactService.approve(product.getId(), buyer1.getId(), buyer2.getId()))
                .hasFieldOrPropertyWithValue("exceptionStatus", ExceptionStatus.NOT_SELLER);
    }

    @Test
    @DisplayName("중복하여 판매승인하는 경우 예외가 발생한다")
    void approveDuplicate() throws Exception {

        // given
        transactService.approve(product.getId(), buyer1.getId(), seller.getId());

        // when
        // then
        Assertions.assertThatThrownBy(() -> transactService.approve(product.getId(), buyer1.getId(), seller.getId()))
                .hasFieldOrPropertyWithValue("exceptionStatus", ExceptionStatus.APPROVE_ALREADY);
    }
}

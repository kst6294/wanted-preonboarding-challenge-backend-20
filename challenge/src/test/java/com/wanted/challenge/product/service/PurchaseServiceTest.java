package com.wanted.challenge.product.service;

import com.wanted.challenge.IntegrationTestSupport;
import com.wanted.challenge.account.entity.Account;
import com.wanted.challenge.account.repository.AccountRepository;
import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.product.model.Price;
import com.wanted.challenge.product.model.Quantity;
import com.wanted.challenge.product.model.Reservation;
import com.wanted.challenge.product.repository.ProductRepository;
import com.wanted.challenge.purchase.entity.Purchase;
import com.wanted.challenge.purchase.model.PurchaseDetail;
import com.wanted.challenge.purchase.repository.PurchaseRepository;
import com.wanted.challenge.purchase.service.PurchaseService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

class PurchaseServiceTest extends IntegrationTestSupport {

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    Account buyer1;
    Account buyer2;
    Account seller;
    Product product;

    @BeforeEach
    void setUp() {
        buyer1 = accountRepository.save(new Account("buyer1", "1234"));
        buyer2 = accountRepository.save(new Account("buyer2", "1234"));

        seller = accountRepository.save(new Account("seller", "1234"));

        product = new Product(seller, "상품", new Price(10_000), new Quantity(0));
        ReflectionTestUtils.setField(product, "reservation", Reservation.RESERVE);

        product = productRepository.save(product);

        purchaseRepository.save(new Purchase(buyer1, product, PurchaseDetail.DEPOSIT));
        purchaseRepository.save(new Purchase(buyer2, product, PurchaseDetail.DEPOSIT));

        purchaseRepository.save(new Purchase(buyer1, product, PurchaseDetail.APPROVE));
        purchaseRepository.save(new Purchase(buyer2, product, PurchaseDetail.APPROVE));
    }

    @Test
    @DisplayName("모든 사용자가 제품 구매 확정 시 제품 상태 완료로 변경")
    void confirmAll() throws Exception {

        // when
        purchaseService.confirm(product.getId(), buyer1.getId());
        purchaseService.confirm(product.getId(), buyer2.getId());

        // then
        Product afterProduct = productRepository.findById(product.getId())
                .orElseThrow();

        Assertions.assertThat(afterProduct.getReservation())
                .isEqualByComparingTo(Reservation.COMPLETE);
    }
}

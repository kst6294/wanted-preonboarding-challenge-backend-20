package com.wanted.challenge.product.repository;

import static com.wanted.challenge.product.model.PurchaseDetail.ARRIVAL;
import static com.wanted.challenge.product.model.PurchaseDetail.DELIVERY;
import static com.wanted.challenge.product.model.PurchaseDetail.DEPOSIT;
import static org.assertj.core.groups.Tuple.tuple;

import com.wanted.challenge.IntegrationTestSupport;
import com.wanted.challenge.account.entity.Account;
import com.wanted.challenge.account.repository.AccountRepository;
import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.product.entity.Purchase;
import com.wanted.challenge.product.model.Price;
import com.wanted.challenge.product.response.PurchaseProductResponse;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ProductRepositoryTest extends IntegrationTestSupport {

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("구매한 제품과 가장 마지막 거래 내용 확인")
    void retrievePurchaseProducts() throws Exception {

        // given
        Account seller = accountRepository.save(new Account("seller", "password"));
        Account buyer = accountRepository.save(new Account("buyer", "password"));
        Product product1 = productRepository.save(new Product(seller, "product1", new Price(10_000)));
        Product product2 = productRepository.save(new Product(seller, "product2", new Price(20_000)));

        purchaseRepository.save(new Purchase(buyer, product1, DEPOSIT));
        purchaseRepository.save(new Purchase(buyer, product1, DELIVERY));
        purchaseRepository.save(new Purchase(buyer, product1, ARRIVAL));

        purchaseRepository.save(new Purchase(buyer, product2, DEPOSIT));
        purchaseRepository.save(new Purchase(buyer, product2, DELIVERY));

        // when
        List<PurchaseProductResponse> purchaseProductResponses =
                productRepository.retrievePurchaseProducts(buyer.getId());

        // then
        Assertions.assertThat(purchaseProductResponses)
                .extracting(PurchaseProductResponse::name, PurchaseProductResponse::purchaseDetail)
                .containsExactlyInAnyOrder(tuple("product1", ARRIVAL), tuple("product2", DELIVERY));
    }
}

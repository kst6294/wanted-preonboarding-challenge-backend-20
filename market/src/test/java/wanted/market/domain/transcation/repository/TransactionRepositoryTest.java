package wanted.market.domain.transcation.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import wanted.market.domain.member.repository.MemberRepository;
import wanted.market.domain.member.repository.entity.Member;
import wanted.market.domain.product.repository.ProductRepository;
import wanted.market.domain.product.repository.entity.Product;
import wanted.market.domain.transcation.repository.entity.Transaction;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.*;
import static wanted.market.domain.product.repository.entity.ReservationStatus.RESERVATION;
import static wanted.market.domain.product.repository.entity.ReservationStatus.SALE;
import static wanted.market.domain.transcation.repository.entity.TransactionStatus.TRADE_CANCEL;
import static wanted.market.domain.transcation.repository.entity.TransactionStatus.TRADING;
import static wanted.market.global.dto.Authority.ROLE_USER;

@DataJpaTest
@ActiveProfiles("test")
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProductRepository productRepository;

    private static Member member1;

    private static Member member2;

    @BeforeAll
    public static void setUp(@Autowired MemberRepository memberRepository) {
        member1 = memberRepository.save(Member.builder()
                .email("test@test.com")
                .authority(ROLE_USER)
                .password("1234")
                .build());

        member2 = memberRepository.save(Member.builder()
                .email("test2@test.com")
                .authority(ROLE_USER)
                .password("1234")
                .build());
    }

    @Test
    @DisplayName("제품을 토대로 거래를 시작한다. (거래 생성)")
    void createTransaction() {
        // given
        Product product = productRepository.save(createProduct("라면", 1000, 1));
        Transaction transaction = transactionRepository.save(createTransaction(product, 1000));

        // when
        Optional<Transaction> byId = transactionRepository.findById(transaction.getId());

        // then
        assertThat(byId).isPresent();
        assertThat(byId.get().getMember().getId()).isEqualTo(member2.getId());
        assertThat(byId.get().getProduct().getId()).isEqualTo(product.getId());
        assertThat(byId.get().getTransactionStatus()).isEqualTo(TRADING);
        assertThat(byId.get().getPrice()).isEqualTo(transaction.getPrice());
    }

    @Test
    @DisplayName("한 구매자가 구매요청을 한 경우, 해당 제품의 예약상태를 '예약중' 으로 변경한다.")
    void updateProductReservationStatus() {
        // given
        Product product = productRepository.save(createProduct("라면", 1000, 1));
        Transaction transaction = transactionRepository.save(createTransaction(product, 1000));
        product.updateReservationStatus();

        // when
        Optional<Product> byId = productRepository.findById(product.getId());

        // then
        assertThat(byId).isPresent();
        assertThat(byId.get().getReservationStatus()).isEqualTo(RESERVATION);
    }

    @Test
    @DisplayName("한 구매자가 구매요청을 한 경우, 해당 제품의 수량이 더 남아있으면 수량을 차감하고, 예약상태는 '판매중' 으로 유지한다.")
    void maintainProductReservationStatus() {
        // given
        Product product = productRepository.save(createProduct("라면", 1000, 2));
        Transaction transaction = transactionRepository.save(createTransaction(product, 1000));

        product.updateReservationStatus();

        // when
        Optional<Product> byId = productRepository.findById(product.getId());

        System.out.println(byId.get());

        // then
        assertThat(byId).isPresent();
        assertThat(byId.get().getReservationStatus()).isEqualTo(SALE);
    }

    @Test
    @DisplayName("거래가 취소되는 경우, 해당 수량이 다시 반환되어야 하고, 만약 제품의 예약상태가 '예약중' 인 경우 '판매중' 으로 변경된다.")
    void bringBackQuantity() {
        // given
        Product product = productRepository.save(createProduct("라면", 1000, 1));
        Transaction transaction = transactionRepository.save(createTransaction(product, 1000));
        product.updateReservationStatus();

        // 거래 취소 요청
        transaction.cancelTransaction();
        product.bringBackQuantity();

        // when
        Optional<Product> byId1 = productRepository.findById(product.getId());
        Optional<Transaction> byId2 = transactionRepository.findById(transaction.getId());

        // then
        assertThat(byId1).isPresent();
        assertThat(byId1.get().getReservationStatus()).isEqualTo(SALE);
        assertThat(byId2).isPresent();
        assertThat(byId2.get().getTransactionStatus()).isEqualTo(TRADE_CANCEL);
    }

    @Test
    @DisplayName("거래를 시작한 이후에 해당 제품의 가격이 변경되어도, 거래를 시작할 당시의 가격이 유지된다.")
    void maintainTransactionPrice() {
        // given
        int beforePrice = 1000;
        Product product = productRepository.save(createProduct("라면", beforePrice, 2));
        Transaction transaction = transactionRepository.save(createTransaction(product, 1000));
        product.updateReservationStatus();

        int afterPrice = 2000;
        product.updateProduct(afterPrice);

        // when
        Optional<Product> afterUpdatePrice = productRepository.findById(product.getId());
        Optional<Transaction> byId = transactionRepository.findById(transaction.getId());

        // then
        assertThat(afterUpdatePrice).isPresent();
        assertThat(afterUpdatePrice.get().getId()).isEqualTo(product.getId());
        assertThat(byId).isPresent();
        assertThat(byId.get().getPrice()).isNotEqualTo(afterPrice);
        assertThat(byId.get().getPrice()).isEqualTo(beforePrice);
    }

    @Test
    @DisplayName("거래를 진행할 상대방의 역대 거래 정보를 조회한다. (구매, 판매 포함)")
    void finOpponentTransactionList() {
        // given
        Product product = productRepository.save(createProduct("라면", 1000, 1));
        Product product2 = productRepository.save(createProduct("커피", 4000, 1));

        transactionRepository.save(createTransaction(product, 1000));
        product.updateReservationStatus();

        // when
        List<Product> products = productRepository.findAllByMember(member1);

        List<Transaction> transactions = transactionRepository.findAllByMember(member1);

        // then
        assertThat(products).hasSize(2)
                .extracting("name", "price", "reservationStatus")
                .containsExactlyInAnyOrder(
                        tuple("라면", 1000, RESERVATION),
                        tuple("커피", 4000, SALE)
                );

        assertThat(transactions).hasSize(0);
    }


    private static Transaction createTransaction(Product product, int price) {
        return Transaction.builder()
                .member(member2)
                .product(product)
                .transactionStatus(TRADING)
                .price(price)
                .build();
    }

    private static Product createProduct(String name, int price, int quantity) {
        return Product.builder()
                .name(name)
                .member(member1)
                .price(price)
                .reservationStatus(SALE)
                .remainQuantity(quantity)
                .content("판매중입니다.")
                .build();
    }
}
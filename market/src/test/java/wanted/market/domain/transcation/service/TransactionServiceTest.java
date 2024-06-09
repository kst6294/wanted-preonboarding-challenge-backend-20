package wanted.market.domain.transcation.service;

import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import wanted.market.domain.member.repository.MemberRepository;
import wanted.market.domain.member.repository.entity.Member;
import wanted.market.domain.product.repository.ProductRepository;
import wanted.market.domain.product.repository.entity.Product;
import wanted.market.domain.product.service.ProductService;
import wanted.market.domain.product.service.dto.response.ProductDetailResponse;
import wanted.market.domain.product.service.dto.response.ProductListResponse;
import wanted.market.domain.transcation.repository.TransactionRepository;
import wanted.market.domain.transcation.repository.entity.Transaction;
import wanted.market.domain.transcation.service.dto.request.TransactionCancelServiceRequest;
import wanted.market.domain.transcation.service.dto.request.TransactionCreateServiceRequest;
import wanted.market.domain.transcation.service.dto.response.TransactionCreateResponse;
import wanted.market.domain.transcation.service.dto.response.TransactionListResponse;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.*;
import static wanted.market.domain.product.repository.entity.ReservationStatus.RESERVATION;
import static wanted.market.domain.product.repository.entity.ReservationStatus.SALE;
import static wanted.market.domain.transcation.repository.entity.TransactionStatus.TRADING;
import static wanted.market.global.dto.Authority.ROLE_USER;

@SpringBootTest
@ActiveProfiles("test")
class TransactionServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    private static Member member1;

    private static Member member2;

    @BeforeEach
    void setUp() {
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

    @AfterEach
    void tearDown() {
        transactionRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("제품을 토대로 거래를 시작하면 제품의 수량을 확인하고 필요할 경우 예약상태를 변경한다.")
    void createTransaction() {
        // given
        Product product = productRepository.save(createProduct("라면", 1000, 1));

        TransactionCreateServiceRequest request = createTransaction(product);

        TransactionCreateResponse transaction = transactionService.createTransaction(request);

        // when
        Optional<Transaction> byId = transactionRepository.findById(transaction.getTransactionId());
        Optional<Product> byId2 = productRepository.findById(product.getId());

        // then
        assertThat(byId).isPresent();
        assertThat(byId.get().getProduct().getId()).isEqualTo(product.getId());
        assertThat(byId2).isPresent();
        assertThat(byId2.get().getReservationStatus()).isEqualTo(RESERVATION);
    }

    @Test
    @DisplayName("거래를 진행할 상대방의 역대 거래 정보를 조회한다.")
    void findTransactionWithMember() {
        // given
        Product product = productRepository.save(createProduct("라면", 1000, 1));
        Product product2 = productRepository.save(createProduct("커피", 4000, 1));

        TransactionCreateResponse transaction = transactionService.createTransaction(createTransaction(product));

        // when
        List<TransactionListResponse> transactions = transactionService.findTransactionWithMember(member2.getEmail());

        // then
        assertThat(transactions).hasSize(1)
                .extracting("name", "price", "transactionStatus")
                .containsExactlyInAnyOrder(
                        tuple("라면", 1000, TRADING)
                );
    }

    @Test
    @DisplayName("거래가 취소되는 경우, 해당 수량이 다시 반환되어야 하고, 만약 제품의 예약상태가 '예약중' 인 경우 '판매중' 으로 변경된다.")
    void bringBackQuantity() {
        // given
        Product product = productRepository.save(createProduct("라면", 1000, 1));
        TransactionCreateResponse transaction = transactionService.createTransaction(createTransaction(product));

        TransactionCancelServiceRequest request = TransactionCancelServiceRequest.builder()
                .email(member2.getEmail())
                .transactionId(transaction.getTransactionId())
                .build();

        // when
        boolean result = transactionService.cancelTransaction(request);
        ProductDetailResponse productDetail = productService.findProductDetail(product.getId());


        // then
        assertThat(result).isTrue();
        assertThat(productDetail.getReservationStatus()).isEqualTo(SALE);
    }

    @Test
    @DisplayName("판매자도 거래를 승낙하고 거래가 완료된다.")
    void completeTransaction() {
        // given
        Product product = productRepository.save(createProduct("라면", 1000, 1));
        TransactionCreateResponse transaction = transactionService.createTransaction(createTransaction(product));

        // when
        boolean result = transactionService.completeTransaction(transaction.getTransactionId());

        // then
        assertThat(result).isTrue();
    }

    private static TransactionCreateServiceRequest createTransaction(Product product) {
        return TransactionCreateServiceRequest.builder()
                .email(member2.getEmail())
                .productId(product.getId())
                .price(1000)
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
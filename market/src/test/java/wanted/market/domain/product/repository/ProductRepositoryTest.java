package wanted.market.domain.product.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import wanted.market.domain.member.repository.MemberRepository;
import wanted.market.domain.member.repository.entity.Member;
import wanted.market.domain.product.repository.entity.Product;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.groups.Tuple.*;
import static wanted.market.domain.product.repository.entity.ReservationStatus.*;
import static wanted.market.global.dto.Authority.*;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    private static Member member;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(Member.builder()
                .email("test@test.com")
                .authority(ROLE_USER)
                .password("1234")
                .build());
    }

    @Test
    @DisplayName("제품정보를 입력하고 제품을 등록한다.")
    void registerProduct() {
        // given
        Product product = createProduct("라면", 1000, 1);

        // when
        Product save = productRepository.save(product);

        // then
        assertThat(save.getName()).isEqualTo(product.getName());
        assertThat(save.getPrice()).isEqualTo(product.getPrice());
        assertThat(save.getReservationStatus()).isEqualTo(product.getReservationStatus());
    }

    @Test
    @DisplayName("페이징 처리가 된 제품의 목록을 조회한다.")
    void findProductList() {
        // given
        Product product1 = createProduct("라면", 1000, 1);
        Product product2 = createProduct("담배", 4500, 1);
        Product product3 = createProduct("커피", 2000, 1);
        Product product4 = createProduct("과자", 1500, 1);
        productRepository.saveAll(List.of(product1, product2, product3, product4));

        int page = 0; // page 위치에 있는 값은 0부터 시작한다.
        int pageLimit = 3; // 한페이지에 보여줄 글 개수

        // when
        Page<Product> products = productRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        // then
        assertThat(products).hasSize(3)
                .extracting("name", "price").containsExactlyInAnyOrder(
                        tuple("과자", 1500),
                        tuple("커피", 2000),
                        tuple("담배", 4500)
                );
    }

    @Test
    @DisplayName("한 제품을 상세조회한다.")
    void findProductDetail() {
        // given
        Product product = productRepository.save(createProduct("라면", 1000, 1));

        // when
        Optional<Product> byId = productRepository.findById(product.getId());

        // then
        assertThat(byId).isPresent();
        assertThat(byId.get().getName()).isEqualTo(product.getName());
        assertThat(byId.get().getPrice()).isEqualTo(product.getPrice());
        assertThat(byId.get().getReservationStatus()).isEqualTo(product.getReservationStatus());
    }

    @Test
    @DisplayName("한 구매자가 구매요청을 한 경우, 해당 제품의 예약상태를 '예약중' 으로 변경한다.")
    void updateProductReservationStatus() {
        // given
        Product product = productRepository.save(createProduct("라면", 1000, 1));
        product.updateReservationStatus();

        // when
        Optional<Product> byId = productRepository.findById(product.getId());

        // then
        assertThat(byId).isPresent();
        assertThat(byId.get().getReservationStatus()).isEqualTo(RESERVATION);
    }

    @Test
    @DisplayName("한 구매자가 구매요청을 한 경우, 해당 제품의 수량이 더 남아있으면 예약상태는 '판매중' 으로 유지한다.")
    void maintainProductReservationStatus() {
        // given
        Product product = productRepository.save(createProduct("라면", 1000, 2));
        product.updateReservationStatus();

        // when
        Optional<Product> byId = productRepository.findById(product.getId());

        System.out.println(byId.get());

        // then
        assertThat(byId).isPresent();
        assertThat(byId.get().getReservationStatus()).isEqualTo(SALE);
    }

    @Test
    @DisplayName("거래가 취소되는 경우, 만약 제품의 예약상태가 '예약중' 인 경우 '판매중' 으로 변경된다.")
    void bringBackQuantity() {
        // given
        Product product = productRepository.save(createProduct("라면", 1000, 1));
        product.updateReservationStatus();

        Optional<Product> byId1 = productRepository.findById(product.getId());
        byId1.get().bringBackQuantity();

        // when
        Optional<Product> byId2 = productRepository.findById(product.getId());

        // then
        assertThat(byId2).isPresent();
        assertThat(byId2.get().getReservationStatus()).isEqualTo(SALE);
    }

    private static Product createProduct(String name, int price, int quantity) {
        return Product.builder()
                .name(name)
                .member(member)
                .price(price)
                .reservationStatus(SALE)
                .remainQuantity(quantity)
                .completeQuantity(0)
                .content("판매중입니다.")
                .build();
    }

}
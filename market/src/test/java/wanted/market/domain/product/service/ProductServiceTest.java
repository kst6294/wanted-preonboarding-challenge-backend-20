package wanted.market.domain.product.service;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import wanted.market.domain.member.repository.MemberRepository;
import wanted.market.domain.member.repository.entity.Member;
import wanted.market.domain.product.repository.ProductRepository;
import wanted.market.domain.product.repository.entity.Product;
import wanted.market.domain.product.service.dto.request.ProductRegisterServiceRequest;
import wanted.market.domain.product.service.dto.response.ProductDetailResponse;
import wanted.market.domain.product.service.dto.response.ProductListResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.*;

import static wanted.market.domain.product.repository.entity.ReservationStatus.SALE;
import static wanted.market.global.dto.Authority.ROLE_USER;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

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

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("제품정보를 입력받아 제품을 등록한다.")
    void registerProduct() {
        // given
        LocalDateTime now = LocalDateTime.now();
        ProductRegisterServiceRequest request = createProductRequest();
        Long id = productService.register(request);

        // when
        Optional<Product> byId = productRepository.findById(id);

        // then
        assertThat(byId).isPresent();
        assertThat(byId.get().getName()).isEqualTo(request.getName());
        assertThat(byId.get().getPrice()).isEqualTo(request.getPrice());
        assertThat(byId.get().getContent()).isEqualTo(request.getContent());
        assertThat(byId.get().getCreatedDateTime()).isAfter(now);
        assertThat(byId.get().getCreatedDateTime()).isEqualTo(byId.get().getModifiedDateTime());
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

        int page = 0; // page 위치에 있는 값은 0부터 시작
        int pageLimit = 3; // 한페이지에 보여줄 글 개수

        // when
        List<ProductListResponse> list = productService.findProductList(page);

        // then
        assertThat(list).hasSize(pageLimit)
                .extracting("name", "price")
                .containsExactlyInAnyOrder(
                        tuple("과자", 1500),
                        tuple("커피", 2000),
                        tuple("담배", 4500)
                );
    }

    @Test
    @DisplayName("원하는 제품을 상세조회한다.")
    void findProductDetail() {
        // given
        Product product = productRepository.save(createProduct("라면", 1000, 1));

        // when
        ProductDetailResponse response = productService.findProductDetail(product.getId());

        // then
        assertThat(response.getName()).isEqualTo(product.getName());
        assertThat(response.getEmail()).isEqualTo(product.getMember().getEmail());
        assertThat(response.getPrice()).isEqualTo(product.getPrice());
    }

    private static ProductRegisterServiceRequest createProductRequest() {
        return ProductRegisterServiceRequest.builder()
                .name("라면")
                .email(member.getEmail())
                .price(1000)
                .reservationStatus(SALE)
                .quantity(1)
                .content("라면입니다.")
                .build();
    }

    private static Product createProduct(String name, int price, int quantity) {
        return Product.builder()
                .name(name)
                .member(member)
                .price(price)
                .reservationStatus(SALE)
                .quantity(quantity)
                .content("판매중입니다.")
                .build();
    }
}
package com.example.wanted.product.service;

import com.example.wanted.mock.FakeOrderRepository;
import com.example.wanted.mock.FakeProductRepository;
import com.example.wanted.mock.FakeUserRepository;
import com.example.wanted.module.exception.ResourceNotFoundException;
import com.example.wanted.product.domain.Product;
import com.example.wanted.product.domain.ProductCreate;
import com.example.wanted.product.domain.ProductSellingStatus;
import com.example.wanted.product.service.response.ProductResponse;
import com.example.wanted.user.domain.Role;
import com.example.wanted.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductServiceTest {
    @Autowired
    private ProductService productService;
    private FakeProductRepository fakeProductRepository;
    private FakeUserRepository fakeUserRepository;

    @BeforeEach
    void init() {
        this.fakeUserRepository = new FakeUserRepository();
        this.fakeProductRepository = new FakeProductRepository();
        this.productService = ProductService.builder()
                .productRepository(fakeProductRepository)
                .userRepository(fakeUserRepository)
                .build();

        User user1 = User.builder()
                .id(1L)
                .account("test@gmail.com")
                .password("test1234")
                .name("홍길동")
                .role(Role.USER)
                .build();

        fakeUserRepository.save(user1);
    }
    private String name;
    private int price;
    private int quantity;
    @Test
    void ProductCreate로_Product를_생성_할_수_있다(){
        //given
        User user1 = User.builder()
                .id(1L)
                .account("test@gmail.com")
                .password("test1234")
                .name("홍길동")
                .role(Role.USER)
                .build();

        fakeUserRepository.save(user1);

        ProductCreate productCreate = ProductCreate.builder()
                .name("아이스크림")
                .quantity(100)
                .price(1000)
                .build();

        //when
        Long productId = productService.register(productCreate, 1L);

        //then
        Product product = fakeProductRepository.findById(productId).orElseThrow();
        assertThat(product.getId()).isNotNull();
        assertThat(product.getName()).isEqualTo("아이스크림");
        assertThat(product.getQuantity()).isEqualTo(100);
        assertThat(product.getPrice()).isEqualTo(1000);
        assertThat(product.getSellingStatus()).isEqualTo(ProductSellingStatus.SELLING);
        assertThat(product.getSeller().getId()).isEqualTo(1L);
    }

    @Test
    void id로_Product를_조회_할_수_있다(){
        //given
        User user1 = User.builder()
                .id(1L)
                .account("test@gmail.com")
                .password("test1234")
                .name("홍길동")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(user1);

        Product product = Product.builder()
                .id(1L)
                .name("아이스크림")
                .quantity(100)
                .price(1000)
                .seller(user1)
                .sellingStatus(ProductSellingStatus.SELLING)
                .build();
        fakeProductRepository.save(product);

        //when
        ProductResponse response = productService.getById(1L);

        //then
        assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo("아이스크림");
        assertThat(response.getQuantity()).isEqualTo(100);
        assertThat(response.getPrice()).isEqualTo(1000);
        assertThat(response.getProductSellingStatus()).isEqualTo(ProductSellingStatus.SELLING);
        assertThat(response.getSeller().getName()).isEqualTo("홍길동");
    }

    @Test
    void 존재하지_않는_id로_Product를_조회_시_예외가_발생한다(){
        //given
        User user1 = User.builder()
                .id(1L)
                .account("test@gmail.com")
                .password("test1234")
                .name("홍길동")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(user1);

        //when
        //then
        assertThatThrownBy(() ->
                productService.getById(1L)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void Product_리스트를_조회_할_수_있다(){
        //given
        User user1 = User.builder()
                .id(1L)
                .account("test@gmail.com")
                .password("test1234")
                .name("홍길동")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(user1);

        Product product1 = Product.builder()
                .id(1L)
                .name("아이스크림")
                .quantity(100)
                .price(1000)
                .seller(user1)
                .sellingStatus(ProductSellingStatus.SELLING)
                .build();
        fakeProductRepository.save(product1);

        Product product2 = Product.builder()
                .id(2L)
                .name("포카칩")
                .quantity(100)
                .price(1000)
                .seller(user1)
                .sellingStatus(ProductSellingStatus.SELLING)
                .build();
        fakeProductRepository.save(product2);

        Product product3 = Product.builder()
                .id(3L)
                .name("꼬북칩")
                .quantity(100)
                .price(1000)
                .seller(user1)
                .sellingStatus(ProductSellingStatus.SELLING)
                .build();
        fakeProductRepository.save(product3);

        //when
        List<ProductResponse> response = productService.getList();

        //then
        assertThat(response).hasSize(3);
    }

}
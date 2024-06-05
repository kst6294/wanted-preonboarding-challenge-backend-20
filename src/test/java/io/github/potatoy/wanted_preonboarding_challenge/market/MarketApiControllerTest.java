package io.github.potatoy.wanted_preonboarding_challenge.market;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.potatoy.wanted_preonboarding_challenge.market.dto.ProductDto;
import io.github.potatoy.wanted_preonboarding_challenge.market.entity.Product;
import io.github.potatoy.wanted_preonboarding_challenge.market.entity.ProductRepository;
import io.github.potatoy.wanted_preonboarding_challenge.market.util.TestMarketUtil;
import io.github.potatoy.wanted_preonboarding_challenge.user.entity.User;
import io.github.potatoy.wanted_preonboarding_challenge.user.entity.UserRepository;
import io.github.potatoy.wanted_preonboarding_challenge.user.util.TestUserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class MarketApiControllerTest {

  @Autowired protected MockMvc mockMvc;
  @Autowired protected ObjectMapper objectMapper; // JSON 직렬화, 역직렬화를 위한 클래스
  @Autowired private WebApplicationContext context;
  @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired UserRepository userRepository;
  @Autowired ProductRepository productRepository;

  TestUserUtil testUserUtil;
  TestMarketUtil testMarketUtil;

  @BeforeEach
  public void mockMvcSetup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    userRepository.deleteAll();
    productRepository.deleteAll();
    this.testUserUtil = new TestUserUtil(bCryptPasswordEncoder, userRepository);
    this.testMarketUtil = new TestMarketUtil(productRepository);
  }

  @DisplayName("registrationProduct(): 새로운 상품 등록")
  @WithMockUser(username = "user@mail.com")
  @Test
  public void successRegistrationProduct() throws Exception {
    final String url = "/api/market/products";
    final String email = "user@mail.com";
    final String password = "test";
    final String productName = "상품1";
    final Long productPrice = 20_000L;

    User user = testUserUtil.createTestUser(email, password, null);

    ProductDto.RegisterRequest request = new ProductDto.RegisterRequest();
    request.setName(productName);
    request.setPrice(productPrice);

    final String requestBody = objectMapper.writeValueAsString(request);

    ResultActions resultActions =
        mockMvc.perform(
            post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody));

    resultActions
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNotEmpty())
        .andExpect(jsonPath("$.name").value(productName))
        .andExpect(jsonPath("$.price").value(productPrice))
        .andExpect(jsonPath("$.sellerUser.email").value(user.getEmail()));
  }

  @DisplayName("reserveProduct(): 새로운 예약자 등록")
  @WithMockUser(username = "user2@mail.com")
  @Test
  public void successReserveProduct() throws Exception {
    final String url = "/api/market/products/{productId}/reserve";
    final User sellerUser = testUserUtil.createTestUser("user1@mail.com", "test", null);
    final User buyerUser = testUserUtil.createTestUser("user2@mail.com", "test", null);
    final Product product = testMarketUtil.createProduct(sellerUser, "상품1", 20_000L, null);

    ResultActions result =
        mockMvc.perform(
            post(url.replace("{productId}", product.getId() + ""))
                .contentType(MediaType.APPLICATION_JSON_VALUE));

    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.buyerUser.userId").value(buyerUser.getId()));
  }

  @DisplayName("getProducts(): 거래자간의 거래 기록 조회 성공")
  @WithMockUser(username = "user1@mail.com")
  @Test
  public void successBetweenTraders() throws Exception {
    final String url = "/api/market/products/transactions?productId={productId}";
    final User user1 = testUserUtil.createTestUser("user1@mail.com", "test", null);
    final User user2 = testUserUtil.createTestUser("user2@mail.com", "test", null);
    testUserUtil.createTestUser("user3@mail.com", "test", null);
    final Product product = testMarketUtil.createProduct(user1, "product", 20_000L, user2);

    ResultActions result =
        mockMvc.perform(
            get(url.replace("{productId}", product.getId() + ""))
                .contentType(MediaType.APPLICATION_JSON_VALUE));

    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(product.getId()))
        .andExpect(jsonPath("$[0].sellerUser.userId").value(user1.getId()))
        .andExpect(jsonPath("$[0].buyerUser.userId").value(user2.getId()));
  }

  @DisplayName("getProducts(): 자신의 거래 기록 조회 성공")
  @WithMockUser(username = "user1@mail.com")
  @Test
  public void successMyTraders() throws Exception {
    final String url = "/api/market/products/transactions";
    final User user1 = testUserUtil.createTestUser("user1@mail.com", "test", null);
    final User user2 = testUserUtil.createTestUser("user2@mail.com", "test", null);
    final Product product1 = testMarketUtil.createProduct(user1, "product", 20_000L, null);
    final Product product2 = testMarketUtil.createProduct(user2, "product", 20_000L, user1);

    ResultActions result = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON_VALUE));

    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(product1.getId()))
        .andExpect(jsonPath("$[0].sellerUser.userId").value(user1.getId()))
        .andExpect(jsonPath("$[1].id").value(product2.getId()))
        .andExpect(jsonPath("$[1].buyerUser.userId").value(user1.getId()));
  }

  @DisplayName("approveSale(): 판매 승인 처리")
  @WithMockUser(username = "user1@mail.com")
  @Test
  public void successApproveSale() throws Exception {
    final String url = "/api/market/products/{productId}/purchase";
    final User user1 = testUserUtil.createTestUser("user1@mail.com", "test", null);
    final User user2 = testUserUtil.createTestUser("user2@mail.com", "test", null);
    final Product product = testMarketUtil.createProduct(user1, "product", 20_000L, user2);

    ResultActions result =
        mockMvc.perform(
            post(url.replace("{productId}", product.getId() + ""))
                .contentType(MediaType.APPLICATION_JSON_VALUE));

    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(product.getId()))
        .andExpect(jsonPath("$.state").value("COMPLETE"));
  }

  @DisplayName("getAllProducts(): 상품 목록 조회")
  @Test
  public void successGetAllProducts() throws Exception {
    final String url = "/api/market/products";
    final User user1 = testUserUtil.createTestUser("user1@mail.com", "test", null);
    final User user2 = testUserUtil.createTestUser("user2@mail.com", "test", null);
    final Product product1 = testMarketUtil.createProduct(user1, "product1", 20_000L, user2);
    final Product product2 = testMarketUtil.createProduct(user2, "product2", 30_000L, null);

    ResultActions result = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON_VALUE));

    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(product1.getId()))
        .andExpect(jsonPath("$[0].name").value(product1.getName()))
        .andExpect(jsonPath("$[1].id").value(product2.getId()))
        .andExpect(jsonPath("$[1].name").value(product2.getName()));
  }

  @DisplayName("getProduct(): 특정 상품 상세 조회")
  @Test
  public void successGetProduct() throws Exception {
    final String url = "/api/market/products/{productId}";
    final User user = testUserUtil.createTestUser("user@mail.com", "test", null);
    final Product product = testMarketUtil.createProduct(user, "product1", 20_000L, null);

    ResultActions result =
        mockMvc.perform(
            get(url.replace("{productId}", product.getId() + ""))
                .contentType(MediaType.APPLICATION_JSON_VALUE));

    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(product.getId()))
        .andExpect(jsonPath("$.name").value(product.getName()))
        .andExpect(jsonPath("$.price").value(product.getPrice()))
        .andExpect(jsonPath("$.sellerUser.email").value(user.getEmail()));
  }
}

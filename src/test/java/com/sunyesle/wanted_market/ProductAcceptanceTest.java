package com.sunyesle.wanted_market;

import com.sunyesle.wanted_market.dto.*;
import com.sunyesle.wanted_market.repository.MemberRepository;
import com.sunyesle.wanted_market.repository.ProductRepository;
import com.sunyesle.wanted_market.support.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.sunyesle.wanted_market.support.AuthSteps.로그인_요청;
import static com.sunyesle.wanted_market.support.AuthSteps.회원가입_요청;
import static com.sunyesle.wanted_market.support.CommonSupporter.*;
import static com.sunyesle.wanted_market.support.ProductSteps.제품_등록_요청;
import static com.sunyesle.wanted_market.support.ProductSteps.제품_조회_요청;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

class ProductAcceptanceTest extends AcceptanceTest {
    private static final String EMAIL = "test@email.com";
    private static final String PASSWORD = "password1";
    private String token;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        super.setUp();
        productRepository.deleteAll();
        memberRepository.deleteAll();

        회원가입_요청(new SignupRequest("회원명", EMAIL, PASSWORD));
        token = 로그인_요청(new SigninRequest(EMAIL, PASSWORD)).as(SigninResponse.class).getToken();
    }

    @Test
    void 제품을_등록한다() {
        String name = "스위치";
        Integer price = 300000;
        Integer quantity = 2;
        ProductRequest productRequest = new ProductRequest(name, price, quantity);

        ExtractableResponse<Response> response = 제품_등록_요청(token, productRequest);

        등록에_성공한다(response);
        ProductResponse productResponse = response.as(ProductResponse.class);
        assertThat(productResponse.getId()).isNotNull();
    }

    @Test
    void 제품을_조회한다() {
        ProductRequest productRequest = new ProductRequest("스위치", 300000, 2);
        ExtractableResponse<Response> productResponse = 제품_등록_요청(token, productRequest);
        ProductResponse savedProductInfo = productResponse.as(ProductResponse.class);

        ExtractableResponse<Response> response = 제품_조회_요청(savedProductInfo.getId());

        조회에_성공한다(response);
    }

    @Test
    void 존재하지_않는_제품을_조회할_경우_제품조회에_실패한다() {
        ExtractableResponse<Response> response =
                given()
                        .log().all()
                        .basePath("/api/v1/products/" + 100)
                .when()
                        .get()
                .then()
                        .log().all()
                        .extract();

        찾을_수_없는_정보로_인해_요청에_실패한다(response);
    }

    @Test
    void 제품_목록을_조회한다() {
        ProductRequest productRequest1 = new ProductRequest("스위치", 300000, 2);
        ProductRequest productRequest2 = new ProductRequest("당근", 1000, 10);
        제품_등록_요청(token, productRequest1);
        제품_등록_요청(token, productRequest2);

        ExtractableResponse<Response> response =
                given()
                        .log().all()
                        .basePath("/api/v1/products")
                .when()
                        .get()
                .then()
                        .log().all()
                        .extract();

        조회에_성공한다(response);
        List<ProductDetailResponse> products = response.jsonPath().getList(".", ProductDetailResponse.class);
        assertThat(products).hasSize(2);
    }
}

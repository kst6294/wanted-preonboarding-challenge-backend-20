package com.sunyesle.wanted_market;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunyesle.wanted_market.dto.ProductDetailResponse;
import com.sunyesle.wanted_market.dto.ProductRequest;
import com.sunyesle.wanted_market.dto.ProductResponse;
import com.sunyesle.wanted_market.repository.ProductRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductAcceptanceTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        productRepository.deleteAll();
    }

    @Test
    void 제품을_등록한다() throws JsonProcessingException {
        String name = "스위치";
        Integer price = 300000;
        ProductRequest productRequest = new ProductRequest(name, price);

        ExtractableResponse<Response> response = 제품_등록_요청(productRequest);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        ProductResponse productResponse = response.as(ProductResponse.class);
        assertThat(productResponse.getId()).isNotNull();
    }

    private ExtractableResponse<Response> 제품_등록_요청(ProductRequest productRequest) throws JsonProcessingException {
        ExtractableResponse<Response> response =
                given()
                        .log().all()
                        .basePath("/api/v1/products")
                        .body(objectMapper.writeValueAsString(productRequest))
                        .contentType(ContentType.JSON)
                .when()
                        .post()
                .then()
                        .log().all()
                        .extract();
        return response;
    }

    @Test
    void 제품을_조회한다() throws JsonProcessingException {
        String name = "스위치";
        Integer price = 300000;
        ProductRequest productRequest = new ProductRequest(name, price);
        ExtractableResponse<Response> productResponse = 제품_등록_요청(productRequest);
        ProductResponse savedProductInfo = productResponse.as(ProductResponse.class);

        ExtractableResponse<Response> response =
                given()
                        .log().all()
                        .basePath("/api/v1/products/" + savedProductInfo.getId())
                .when()
                        .get()
                .then()
                        .log().all()
                        .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 제품_목록을_조회한다() throws JsonProcessingException {
        ProductRequest productRequest1 = new ProductRequest("스위치", 300000);
        ProductRequest productRequest2 = new ProductRequest("당근", 1000);
        제품_등록_요청(productRequest1);
        제품_등록_요청(productRequest2);

        ExtractableResponse<Response> response =
                given()
                        .log().all()
                        .basePath("/api/v1/products")
                .when()
                        .get()
                .then()
                        .log().all()
                        .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        List<ProductDetailResponse> products = response.jsonPath().getList(".", ProductDetailResponse.class);
        assertThat(products).hasSize(2);
    }
}

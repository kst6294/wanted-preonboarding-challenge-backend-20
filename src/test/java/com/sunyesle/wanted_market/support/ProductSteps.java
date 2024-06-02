package com.sunyesle.wanted_market.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunyesle.wanted_market.dto.ProductRequest;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class ProductSteps {

    @SneakyThrows
    public static ExtractableResponse<Response> 제품_등록_요청(String token, ProductRequest productRequest) {
        ObjectMapper objectMapper = new ObjectMapper();

        ExtractableResponse<Response> response =
                given()
                        .log().all()
                        .basePath("/api/v1/products")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(ContentType.JSON)
                        .body(objectMapper.writeValueAsString(productRequest))
                .when()
                        .post()
                .then()
                        .log().all()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract();
        return response;
    }
}

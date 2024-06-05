package com.sunyesle.wanted_market.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunyesle.wanted_market.dto.OfferRequest;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;

import static io.restassured.RestAssured.given;

public class OfferSteps {

    @SneakyThrows
    public static ExtractableResponse<Response> 제품_예약_요청(OfferRequest offerRequest, String token) {
        ObjectMapper objectMapper = new ObjectMapper();

        return given()
                .log().all()
                .basePath("/api/v1/offers")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(offerRequest))
        .when()
                .post()
        .then()
                .log().all()
                .extract();
    }
}

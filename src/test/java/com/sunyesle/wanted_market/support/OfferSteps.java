package com.sunyesle.wanted_market.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunyesle.wanted_market.dto.CreateOfferRequest;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;

import static io.restassured.RestAssured.given;

public class OfferSteps {

    @SneakyThrows
    public static ExtractableResponse<Response> 구매_요청(CreateOfferRequest createOfferRequest, String token) {
        ObjectMapper objectMapper = new ObjectMapper();

        return given()
                .log().all()
                .basePath("/api/v1/offers")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(createOfferRequest))
        .when()
                .post()
        .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 구매_승인처리_요청(Long offerId, String token) {
        ExtractableResponse<Response> response =
                given()
                        .log().all()
                        .basePath("/api/v1/offers/" + offerId + "/accept")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when()
                        .put()
                .then()
                        .log().all()
                        .extract();
        return response;
    }

    public static ExtractableResponse<Response> 구매_거절처리_요청(Long offerId, String token) {
        ExtractableResponse<Response> response =
                given()
                        .log().all()
                        .basePath("/api/v1/offers/" + offerId + "/decline")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when()
                        .put()
                .then()
                        .log().all()
                        .extract();
        return response;
    }

    public static ExtractableResponse<Response> 구매_확정처리_요청(Long offerId, String token) {
        ExtractableResponse<Response> response =
                given()
                        .log().all()
                        .basePath("/api/v1/offers/" + offerId + "/confirm")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when()
                        .put()
                .then()
                        .log().all()
                        .extract();
        return response;
    }
}

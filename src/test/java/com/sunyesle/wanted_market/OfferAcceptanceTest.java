package com.sunyesle.wanted_market;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunyesle.wanted_market.dto.*;
import com.sunyesle.wanted_market.enums.OfferStatus;
import com.sunyesle.wanted_market.repository.MemberRepository;
import com.sunyesle.wanted_market.repository.OfferRepository;
import com.sunyesle.wanted_market.repository.ProductRepository;
import com.sunyesle.wanted_market.support.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import static com.sunyesle.wanted_market.support.AuthSteps.로그인_요청;
import static com.sunyesle.wanted_market.support.AuthSteps.회원가입_요청;
import static com.sunyesle.wanted_market.support.OfferSteps.제품_예약_요청;
import static com.sunyesle.wanted_market.support.ProductSteps.제품_등록_요청;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

class OfferAcceptanceTest extends AcceptanceTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OfferRepository offerRepository;

    private String sellerToken;
    private String buyerToken;
    private Long savedProductId;

    @BeforeEach
    public void setUp() {
        super.setUp();
        memberRepository.deleteAll();
        productRepository.deleteAll();
        offerRepository.deleteAll();

        String sellerEmail = "seller@email.com";
        String sellerPassword = "password1";
        회원가입_요청(new SignupRequest("판매자", sellerEmail, sellerPassword));
        sellerToken = 로그인_요청(new SigninRequest(sellerEmail, sellerPassword)).as(SigninResponse.class).getToken();

        String buyerEmail = "buyer@email.com";
        String buyerPassword = "password2";
        회원가입_요청(new SignupRequest("구매자", buyerEmail, buyerPassword));
        buyerToken = 로그인_요청(new SigninRequest(buyerEmail, buyerPassword)).as(SigninResponse.class).getToken();

        savedProductId = 제품_등록_요청(sellerToken, new ProductRequest("스위치", 300000)).as(ProductResponse.class).getId();
    }

    @Test
    void 제품을_예약한다() throws JsonProcessingException {
        OfferRequest offerRequest = new OfferRequest(savedProductId);

        ExtractableResponse<Response> response = 제품_예약_요청(offerRequest, buyerToken);

        OfferResponse offerResponse = response.as(OfferResponse.class);
        assertThat(offerResponse.getId()).isNotNull();
        assertThat(offerResponse.getStatus()).isEqualTo(OfferStatus.OPEN);
    }

    @Test
    void 제품_예약_요청을_승인한다() {
        OfferRequest offerRequest = new OfferRequest(savedProductId);
        Long offerId = 제품_예약_요청(offerRequest, buyerToken).as(OfferResponse.class).getId();

        ExtractableResponse<Response> response =
                given()
                        .log().all()
                        .basePath("/api/v1/offers/" + offerId + "/accept")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + sellerToken)
                        .when()
                        .put()
                        .then()
                        .log().all()
                        .statusCode(HttpStatus.OK.value())
                        .extract();

        OfferResponse offerResponse = response.as(OfferResponse.class);
        assertThat(offerResponse.getId()).isNotNull();
        assertThat(offerResponse.getStatus()).isEqualTo(OfferStatus.ACCEPTED);
    }

    @Test
    void 제품_예약_요청을_거절한다() {
        OfferRequest offerRequest = new OfferRequest(savedProductId);
        Long offerId = 제품_예약_요청(offerRequest, buyerToken).as(OfferResponse.class).getId();

        ExtractableResponse<Response> response =
                given()
                        .log().all()
                        .basePath("/api/v1/offers/" + offerId + "/decline")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + sellerToken)
                .when()
                        .put()
                .then()
                        .log().all()
                        .statusCode(HttpStatus.OK.value())
                        .extract();

        OfferResponse offerResponse = response.as(OfferResponse.class);
        assertThat(offerResponse.getId()).isNotNull();
        assertThat(offerResponse.getStatus()).isEqualTo(OfferStatus.DECLINED);
    }
}

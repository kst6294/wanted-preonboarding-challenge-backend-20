package com.sunyesle.wanted_market;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunyesle.wanted_market.dto.SignupRequest;
import com.sunyesle.wanted_market.dto.SignupResponse;
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

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthAcceptanceTest {

    @Autowired
    ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void 회원가입을_한다() throws JsonProcessingException {
        String name = "김이름";
        String email = "test@email.com";
        String password = "password";
        SignupRequest signupRequest = new SignupRequest(name, email, password);

        ExtractableResponse<Response> response =
                given()
                        .log().all()
                        .basePath("/api/v1/auth/signup")
                        .body(objectMapper.writeValueAsString(signupRequest))
                        .contentType(ContentType.JSON)
                .when()
                        .post()
                .then()
                        .log().all()
                        .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        SignupResponse signupResponse =  response.as(SignupResponse.class);
        assertThat(signupResponse.getId()).isNotNull();
    }
}

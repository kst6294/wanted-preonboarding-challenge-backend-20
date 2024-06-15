package com.sunyesle.wanted_market.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunyesle.wanted_market.member.dto.SigninRequest;
import com.sunyesle.wanted_market.member.dto.SignupRequest;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class AuthSteps {

    @SneakyThrows
    public static ExtractableResponse<Response> 회원가입_요청(SignupRequest signupRequest) {
        ObjectMapper objectMapper = new ObjectMapper();

        ExtractableResponse<Response> response =
                given()
                        .log().all()
                        .basePath("/api/v1/auth/signup")
                        .contentType(ContentType.JSON)
                        .body(objectMapper.writeValueAsString(signupRequest))
                .when()
                        .post()
                .then()
                        .log().all()
                        .extract();
        return response;
    }

    @SneakyThrows
    public static ExtractableResponse<Response> 로그인_요청(SigninRequest signinRequest) {
        ObjectMapper objectMapper = new ObjectMapper();

        ExtractableResponse<Response> response =
                given()
                        .log().all()
                        .basePath("/api/v1/auth/signin")
                        .contentType(ContentType.JSON)
                        .body(objectMapper.writeValueAsString(signinRequest))
                .when()
                        .post()
                .then()
                        .log().all()
                        .statusCode(HttpStatus.OK.value())
                        .extract();
        return response;
    }
}

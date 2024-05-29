package org.example.preonboarding.auth.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    private String type;
    private String accessToken;
    private String refreshToken; // TODO
    private Long accessTokenExpired;
    private Long refreshTokenExpired; // TODO
}

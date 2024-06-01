package io.github.potatoy.wanted_preonboarding_challenge.token.dto;

import io.github.potatoy.wanted_preonboarding_challenge.user.dto.UserResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class AuthenticateDto {

  @Getter
  @Setter
  public static class Request {

    @NotNull @Email private String email;
    @NotNull private String password;
  }

  @AllArgsConstructor
  @Getter
  public static class Response {

    private String accessToken;
    private String refreshToken;

    private UserResponse user;
  }
}

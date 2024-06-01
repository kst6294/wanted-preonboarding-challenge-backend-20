package io.github.potatoy.wanted_preonboarding_challenge.token.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class TokenDto {

  @Getter
  @Setter
  public static class Request {

    private String accessToken;
    private String refreshToken;
  }

  @AllArgsConstructor
  @Getter
  @Setter
  public static class Response {

    private String accessToken;
  }
}

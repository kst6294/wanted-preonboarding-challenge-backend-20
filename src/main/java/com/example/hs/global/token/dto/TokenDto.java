package com.example.hs.global.token.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TokenDto {
  private String accessToken;
  private String refreshToken;

  public static TokenDto tokenDtoBuild(String accessToken, String refreshToken) {
    return TokenDto.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }
}

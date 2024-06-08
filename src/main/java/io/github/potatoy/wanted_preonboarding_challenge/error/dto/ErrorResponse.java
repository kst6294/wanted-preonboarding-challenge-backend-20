package io.github.potatoy.wanted_preonboarding_challenge.error.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErrorResponse {

  private int status;
  private String code;
}

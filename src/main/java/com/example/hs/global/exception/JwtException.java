package com.example.hs.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class JwtException extends RuntimeException{
  private int errorCode;
  private String errorMessage;

  public JwtException(ErrorCode errorCode) {
    this.errorCode = errorCode.getHttpCode();
    this.errorMessage = errorCode.getDescription();
  }
}

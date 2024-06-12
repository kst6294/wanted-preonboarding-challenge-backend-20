package com.example.hs.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CustomException extends RuntimeException{
  private int httpCode;
  private String errorMessage;
  private ErrorCode errorCode;

  public CustomException(ErrorCode errorCode) {
    this.httpCode = errorCode.getHttpCode();
    this.errorMessage = errorCode.getDescription();
    this.errorCode = errorCode;
  }
}

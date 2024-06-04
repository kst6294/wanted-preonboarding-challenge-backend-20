package com.example.hs.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReIssueRequest {
  @NotBlank(message = "이전 access token값은 필수입니다.")
  private String oldAccessToken;
}

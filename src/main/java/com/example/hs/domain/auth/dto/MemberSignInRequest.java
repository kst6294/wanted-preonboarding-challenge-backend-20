package com.example.hs.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignInRequest {
  @NotBlank(message = "로그인 아이디는 필수 입니다.")
  private String username; //loginId
  @NotBlank(message = "비밀번호는 필수 입니다.")
  private String password;
}

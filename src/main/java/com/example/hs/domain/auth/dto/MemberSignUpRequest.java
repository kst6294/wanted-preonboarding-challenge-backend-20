package com.example.hs.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignUpRequest {
  @NotBlank(message = "로그인 아이디는 필수 입니다.")
  private String username; //loginId
  @NotBlank(message = "비밀번호는 필수 입니다.")
  private String password;
  @NotBlank(message = "비밀번호 확인은 필수 입니다.")
  private String rePassword;
  @NotBlank(message = "이름은 필수 입니다.")
  private String name;
}

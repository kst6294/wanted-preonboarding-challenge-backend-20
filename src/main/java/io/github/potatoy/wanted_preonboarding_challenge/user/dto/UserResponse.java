package io.github.potatoy.wanted_preonboarding_challenge.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserResponse {

  private Long userId;
  private String email;
  private String nickname;
}

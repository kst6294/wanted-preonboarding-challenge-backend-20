package io.github.potatoy.wanted_preonboarding_challenge.user.dto;

import io.github.potatoy.wanted_preonboarding_challenge.user.entity.User;
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

  public UserResponse(User user) {
    this.userId = user.getId();
    this.email = user.getEmail();
    this.nickname = user.getNickname();
  }
}

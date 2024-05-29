package io.github.potatoy.wanted_preonboarding_challenge.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddUserRequest {

  @NotNull @Email private String email;
  @NotNull private String password;
  @NotNull private String nickname;
}

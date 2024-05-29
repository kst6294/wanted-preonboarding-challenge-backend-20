package io.github.potatoy.wanted_preonboarding_challenge.user;

import io.github.potatoy.wanted_preonboarding_challenge.user.dto.AddUserRequest;
import io.github.potatoy.wanted_preonboarding_challenge.user.dto.UserResponse;
import io.github.potatoy.wanted_preonboarding_challenge.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
public class UserApiController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<UserResponse> signup(@Validated @RequestBody AddUserRequest request) {
    User user = userService.signup(request);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new UserResponse(user.getId(), user.getEmail(), user.getNickname()));
  }
}

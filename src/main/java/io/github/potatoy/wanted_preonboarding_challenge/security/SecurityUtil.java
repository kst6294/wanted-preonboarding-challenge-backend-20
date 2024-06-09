package io.github.potatoy.wanted_preonboarding_challenge.security;

import io.github.potatoy.wanted_preonboarding_challenge.user.entity.User;
import io.github.potatoy.wanted_preonboarding_challenge.user.entity.UserRepository;
import io.github.potatoy.wanted_preonboarding_challenge.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SecurityUtil {

  private final UserRepository userRepository;

  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    User user =
        userRepository
            .findByEmail(userDetails.getUsername())
            .orElseThrow(UserNotFoundException::new);

    return user;
  }
}

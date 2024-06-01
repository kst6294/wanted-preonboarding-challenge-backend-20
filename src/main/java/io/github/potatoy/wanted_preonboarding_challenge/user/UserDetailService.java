package io.github.potatoy.wanted_preonboarding_challenge.user;

import io.github.potatoy.wanted_preonboarding_challenge.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) {
    return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException(email));
  }
}

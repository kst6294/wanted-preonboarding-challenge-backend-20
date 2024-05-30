package io.github.potatoy.wanted_preonboarding_challenge.user;

import io.github.potatoy.wanted_preonboarding_challenge.user.dto.AddUserRequest;
import io.github.potatoy.wanted_preonboarding_challenge.user.entity.User;
import io.github.potatoy.wanted_preonboarding_challenge.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

  private final UserRepository userRepository;

  /**
   * 사용자 회원가입
   *
   * @param dto 사용자 회원가입에 필요한 dto 객체
   * @return User 객체
   */
  public User signup(AddUserRequest dto) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    User user =
        userRepository.save(
            User.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .build());

    log.info(
        "signup. userId={}, userEmail={}, userNickname={}",
        user.getId(),
        user.getEmail(),
        user.getNickname());

    return user;
  }
}

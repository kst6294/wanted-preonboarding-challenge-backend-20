package io.github.potatoy.wanted_preonboarding_challenge.user.util;

import io.github.potatoy.wanted_preonboarding_challenge.user.entity.User;
import io.github.potatoy.wanted_preonboarding_challenge.user.entity.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@AllArgsConstructor
public class TestUserUtil {

  private BCryptPasswordEncoder bCryptPasswordEncoder;
  private UserRepository userRepository;

  /**
   * 새로운 테스트 유저를 생성하고 필드에 저장한다.
   *
   * @param email 사용자 이메일
   * @param password 사용자 패스워드
   * @param nickname 사용자 닉네임. null의 경우 이메일 자동 사용
   * @return User
   */
  public User createTestUser(String email, String password, String nickname) {
    User user =
        userRepository.save(
            User.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .nickname(nickname == null ? email : nickname)
                .build());

    return user;
  }
}

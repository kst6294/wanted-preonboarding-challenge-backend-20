package io.github.potatoy.wanted_preonboarding_challenge.token;

import io.github.potatoy.wanted_preonboarding_challenge.config.jwt.TokenProvider;
import io.github.potatoy.wanted_preonboarding_challenge.token.dto.AuthenticateDto;
import io.github.potatoy.wanted_preonboarding_challenge.token.dto.TokenDto;
import io.github.potatoy.wanted_preonboarding_challenge.token.exception.InvalidTokenException;
import io.github.potatoy.wanted_preonboarding_challenge.user.UserService;
import io.github.potatoy.wanted_preonboarding_challenge.user.dto.UserResponse;
import io.github.potatoy.wanted_preonboarding_challenge.user.entity.User;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class TokenService {

  public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
  public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);

  private final TokenProvider tokenProvider;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final UserService userService;

  /**
   * 새로운 Access Token 생성
   *
   * @param dto TokenDto.Request
   * @return Access Token
   */
  public String createNewAccessToken(TokenDto.Request dto) {
    log.info("createNewAccessToken: Attempting to create a token.");
    // 토큰 유효성 검사에 실패하면 예외 발생
    if (!tokenProvider.validToken(dto.getRefreshToken().toString())) {
      throw new InvalidTokenException();
    }

    Long userId = tokenProvider.getUserId(dto.getRefreshToken());
    User user = userService.findById(userId);

    return tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
  }

  /**
   * 새로운 Access Token과 Refresh Token을 생성
   *
   * @param dto AuthenticateDto.Request
   * @return AuthenticateDto.Response
   */
  public AuthenticateDto.Response createNewTokenSet(AuthenticateDto.Request dto) {
    log.info("createNewTokenSet: Attempting to create a token.");
    // 유저의 이메일과 패스워드를 통해 유저를 확인
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
    Authentication authentication =
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    // 정상적으로 수행될 경우 user 객체 생성
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    User user = userService.findByEmail(userDetails.getUsername());
    log.info("createNewTokenSet: Requesting user. userId={}", user.getId());

    // Token 생성
    String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
    String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);

    UserResponse userResponse = new UserResponse(user.getId(), user.getEmail(), user.getNickname());
    return new AuthenticateDto.Response(accessToken, refreshToken, userResponse);
  }
}

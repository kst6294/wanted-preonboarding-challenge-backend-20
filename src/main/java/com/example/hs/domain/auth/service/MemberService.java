package com.example.hs.domain.auth.service;

import static com.example.hs.global.exception.ErrorCode.ALREADY_EXISTS_LOGIN_ID;
import static com.example.hs.global.exception.ErrorCode.ALREADY_LOGOUT;
import static com.example.hs.global.exception.ErrorCode.INVALID_ACCESS_TOKEN;
import static com.example.hs.global.exception.ErrorCode.INVALID_REFRESH_TOKEN;
import static com.example.hs.global.exception.ErrorCode.INVALID_TOKEN_REISSUE_REQUEST;
import static com.example.hs.global.exception.ErrorCode.NOT_AUTHENTICATE_EMAIL;
import static com.example.hs.global.exception.ErrorCode.NOT_EQUAL_PASSWORD;
import static com.example.hs.global.exception.ErrorCode.NOT_FOUND_MEMBER_LOGIN_ID;
import static com.example.hs.global.exception.ErrorCode.NOT_MATCH_PASSWORD;
import static com.example.hs.global.exception.ErrorCode.NOT_MATCH_TOKEN_USER;
import static com.example.hs.global.token.constant.TokenConstant.BEARER_TYPE;

import com.example.hs.domain.auth.dto.LogoutResponse;
import com.example.hs.domain.auth.dto.MemberDto;
import com.example.hs.domain.auth.dto.MemberSignUpRequest;
import com.example.hs.domain.auth.entity.Member;
import com.example.hs.domain.auth.repository.MemberRepository;
import com.example.hs.domain.auth.type.Authority;
import com.example.hs.global.exception.CustomException;
import com.example.hs.global.mail.service.MailSendService;
import com.example.hs.global.token.dto.TokenDto;
import com.example.hs.global.token.provider.TokenProvider;
import com.example.hs.global.token.repository.TokenRepository;
import jakarta.mail.MessagingException;
import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final MailSendService mailSendService;
  private final TokenProvider tokenProvider;
  private final TokenRepository tokenRepository;
  private final AuthenticationManager authenticationManager;

  @Transactional
  public MemberDto signUp(MemberSignUpRequest request) {
    if (!request.getPassword().equals(request.getRePassword())) {
      throw new CustomException(NOT_EQUAL_PASSWORD);
    }

    if (memberRepository.existsByLoginId(request.getUsername())) {
      throw new CustomException(ALREADY_EXISTS_LOGIN_ID);
    }

    String encPassword = passwordEncoder.encode(request.getPassword());

    try {
      mailSendService.emailAuth(request.getUsername());
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }

    Member saveMember = memberRepository.save(Member.builder()
        .loginId(request.getUsername())
        .password(encPassword)
        .name(request.getName())
        .authority(Authority.ROLE_NOT_YET_MEMBER)
        .build());

    return MemberDto.fromEntity(saveMember, "이메일 인증 후 로그인이 가능합니다.");
  }

  public TokenDto signIn(String loginId, String password) {
//    Member member = validationMember(loginId, password);
    // TODO:// 동일한 select문을 2번 날리지 않기 위해 윗 코드를 주석 처리하였으나 어떤 방법이 알맞은 방법인지 고려해보기
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, password);
    Authentication authentication = authenticationManager.authenticate(authenticationToken);

    boolean isMember = authentication.getAuthorities().stream()
        .anyMatch(authority -> authority.getAuthority().equals("ROLE_MEMBER"));
    if (!isMember) {
      throw new CustomException(NOT_AUTHENTICATE_EMAIL);
    }

    String accessToken = tokenProvider.generateAccessToken(authentication.getName(), authentication);
    String refreshToken = tokenProvider.generateRefreshToken(authentication.getName(), authentication);
    tokenRepository.saveRefreshToken(authentication.getName(), refreshToken);

    return TokenDto.tokenDtoBuild(accessToken, refreshToken);
  }

  public LogoutResponse logout(String accessToken) {
    if (accessToken.startsWith(BEARER_TYPE)) {
      accessToken = accessToken.substring(BEARER_TYPE.length());
    }
    Authentication authentication = tokenProvider.getAuthentication(accessToken);
    if(!tokenProvider.validateToken(accessToken)) {
      throw new CustomException(INVALID_ACCESS_TOKEN);
    }
    String loginId = authentication.getName();
    String refreshToken = tokenRepository.getRefreshToken(loginId);
    if (refreshToken == null) {
      throw new CustomException(ALREADY_LOGOUT);
    }

    tokenRepository.saveInValidAccessToken(loginId, accessToken);
    tokenRepository.deleteRefreshToken(loginId);

    return new LogoutResponse(loginId, "로그아웃 되었습니다.");
  }

  public TokenDto reIssue(String oldAccessToken, String oldRefreshToken) {
    if (!oldRefreshToken.startsWith(BEARER_TYPE)) {
      throw new CustomException(INVALID_TOKEN_REISSUE_REQUEST);
    }

    oldRefreshToken = oldRefreshToken.substring(BEARER_TYPE.length());

    if (!tokenProvider.validateToken(oldRefreshToken)) {
      throw new CustomException(INVALID_REFRESH_TOKEN);
    }

    Authentication authentication = tokenProvider.getAuthentication(oldRefreshToken);
    String loginId = authentication.getName();
    if (!tokenProvider.getUsername(oldRefreshToken).equals(loginId)) {
      throw new CustomException(NOT_MATCH_TOKEN_USER);
    }

    if (oldRefreshToken == null) {
      new CustomException(ALREADY_LOGOUT);
    }

    tokenRepository.saveInValidAccessToken(authentication.getName(), oldAccessToken);

    String accessToken = tokenProvider.generateAccessToken(authentication.getName(), authentication);
    String refreshToken = tokenProvider.generateRefreshToken(authentication.getName(), authentication);

    tokenRepository.saveRefreshToken(authentication.getName(), refreshToken);

    return TokenDto.tokenDtoBuild(accessToken, refreshToken);
  }

  private Member validationMember(String loginId, String password) {
    Member member = memberRepository.findByLoginId(loginId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER_LOGIN_ID));

    if (!this.passwordEncoder.matches(password, member.getPassword())) {
      throw new CustomException(NOT_MATCH_PASSWORD);
    }

    if (member.getAuthority().equals(Authority.ROLE_NOT_YET_MEMBER)) {
      throw new CustomException(NOT_AUTHENTICATE_EMAIL);
    }

    return member;
  }
}

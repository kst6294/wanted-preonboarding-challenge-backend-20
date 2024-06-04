package com.example.hs.domain.auth.service;

import com.example.hs.domain.auth.dto.MemberDto;
import com.example.hs.domain.auth.dto.MemberSignUpRequest;
import com.example.hs.domain.auth.entity.Member;
import com.example.hs.domain.auth.repository.MemberRepository;
import com.example.hs.domain.auth.type.Authority;
import com.example.hs.global.mail.service.MailSendService;
import jakarta.mail.MessagingException;
import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final MailSendService mailSendService;

  @Transactional
  public MemberDto signUp(MemberSignUpRequest request) {
    if (!request.getPassword().equals(request.getRePassword())) {
      throw new RuntimeException("비밀번호와 비밀번호 확인이 맞지 않습니다.");
    }

    if (memberRepository.existsByLoginId(request.getUsername())) {
      throw new RuntimeException("이미 존재하는 로그인 아이디 입니다.");
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
}

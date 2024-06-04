package com.example.hs.global.mail.service;

import static com.example.hs.global.exception.ErrorCode.INVALID_CERTIFIED_NUMBER;
import static com.example.hs.global.exception.ErrorCode.NOT_FOUND_MEMBER_LOGIN_ID;

import com.example.hs.domain.auth.dto.MemberDto;
import com.example.hs.domain.auth.entity.Member;
import com.example.hs.domain.auth.repository.MemberRepository;
import com.example.hs.domain.auth.type.Authority;
import com.example.hs.global.exception.CustomException;
import com.example.hs.global.exception.ErrorCode;
import com.example.hs.global.redis.auth.CertifiedNumberAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailVerifyService {
  private final CertifiedNumberAuthRepository certifiedNumberAuthRepository;
  private final MemberRepository memberRepository;

  public MemberDto verifyEmail(String email, String certificationNumber) {
    if (!isVerifyForEmailAuth(email, certificationNumber)) {
      throw new CustomException(INVALID_CERTIFIED_NUMBER);
    }
    certifiedNumberAuthRepository.removeCertificationNumber(email);

    Member member = memberRepository.findByLoginId(email)
        .orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER_LOGIN_ID));
    member.setAuthority(Authority.ROLE_MEMBER);

    return MemberDto.fromEntity(memberRepository.save(member), "이메일 인증이 완료되었습니다. 로그인 가능합니다.");
  }

  private boolean isVerifyForEmailAuth(String email, String certificationNumber) {
    boolean validatedEmail = isEmailExists(email);
    if (!validatedEmail) {
      throw new RuntimeException("이메일을 찾을 수 없습니다.");
    }

    String number = certifiedNumberAuthRepository.getCertificationNumber(email);
    boolean validateNumber = certificationNumber.equals(number);
    if (!validateNumber) {
      throw new RuntimeException("사용자의 인증 숫자와 맞지 않습니다.");
    }
    return validatedEmail && validateNumber;
  }

  private boolean isEmailExists(String email) {
    return certifiedNumberAuthRepository.hasKeyForEmailAuth(email);
  }
}

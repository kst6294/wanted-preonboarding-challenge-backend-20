package wanted.preonboarding.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wanted.preonboarding.backend.auth.dto.LoginRequest;
import wanted.preonboarding.backend.domain.entity.Member;
import wanted.preonboarding.backend.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    /**
     * 로그인 검증
     */
    public Long signIn(final LoginRequest loginRequest) {
        Member foundMember = memberRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> {
            throw new IllegalArgumentException("회원 정보가 존재하지 않습니다.");
        });

        if (!loginRequest.getPassword().equals(foundMember.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return foundMember.getId();
    }
}

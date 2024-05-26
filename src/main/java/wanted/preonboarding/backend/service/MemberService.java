package wanted.preonboarding.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.preonboarding.backend.domain.entity.Member;
import wanted.preonboarding.backend.dto.request.MemberSaveRequest;
import wanted.preonboarding.backend.repository.MemberRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    public void signUp(MemberSaveRequest memberSaveRequest) {
        Optional<Member> foundMember = memberRepository.findByEmail(memberSaveRequest.getEmail());
        if (foundMember.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        Member member = Member.from(memberSaveRequest);
        memberRepository.save(member);
    }
}

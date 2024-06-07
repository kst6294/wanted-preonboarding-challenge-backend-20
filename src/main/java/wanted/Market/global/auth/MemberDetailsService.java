package wanted.Market.global.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import wanted.Market.domain.member.entity.Member;
import wanted.Market.domain.member.service.MemberService;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // DB에서 사용자 조회
        Member member = memberService.findByUsername(username);

        return new CustomMemberDetails(member);
    }

}
package wanted.Market.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.Market.domain.member.entity.Member;
import wanted.Market.domain.member.repository.MemberRepository;
import wanted.Market.global.exception.AppException;
import wanted.Market.global.exception.ErrorCode.UserErrorCode;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public void join(String username, String password){
        //중복 체크
        memberRepository.findByUsername(username).ifPresent(user -> {
                    throw new AppException(UserErrorCode.USERNAME_DUPLICATED);
        });
        //저장
        memberRepository.save(Member.builder()
                .username(username)
                .password(password)
                .build());
    }

}

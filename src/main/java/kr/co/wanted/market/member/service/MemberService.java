package kr.co.wanted.market.member.service;

import kr.co.wanted.market.common.global.enums.ErrorCode;
import kr.co.wanted.market.common.global.enums.Role;
import kr.co.wanted.market.common.global.error.BizException;
import kr.co.wanted.market.common.global.utils.ContextUtil;
import kr.co.wanted.market.common.global.utils.TokenProvider;
import kr.co.wanted.market.member.dto.MemberJoin;
import kr.co.wanted.market.member.dto.MemberLogin;
import kr.co.wanted.market.member.entity.Member;
import kr.co.wanted.market.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;


    /**
     * 요청 유저의 회원 Entity 를 찾습니다.
     *
     * @return 요청 유저의 Member Entity 또는 정보가 없다면(로그인 정보가 없거나 DB 미존재) Optional.empty()
     */
    public Optional<Member> findCurrentMember() {

        Optional<Long> optionalMemberId = ContextUtil.getMemberId();
        log.debug("currentMember: [{}]", optionalMemberId.orElse(null));
        return optionalMemberId.flatMap(memberRepository::findById);
    }


    /**
     * 회원을 등록합니다.
     *
     * @param memberJoin 등록 정보
     * @throws DataIntegrityViolationException ID값이 중복인 경우 발생합니다.
     */
    @Transactional
    public void joinMember(MemberJoin memberJoin) throws DataIntegrityViolationException {

        memberRepository.save(
                new Member(
                        memberJoin.id(),
                        passwordEncoder.encode(memberJoin.password()),
                        Role.ROLE_USER
                )
        );
    }


    /**
     * 로그인 합니다. (토큰 발급)
     *
     * @param loginRequest 로그인 정보
     * @return 액세스 토큰이 담긴 응답객체
     */
    public MemberLogin.Response login(MemberLogin.Request loginRequest) {

        // 회원 찾기
        Member member = memberRepository.findByLoginId(loginRequest.id())
                .orElseThrow(() -> new BizException(ErrorCode.MEMBER_LOGIN_FAIL));

        // 비밀번호 확인
        if (!passwordEncoder.matches(loginRequest.password(), member.getPassword())) {
            throw new BizException(ErrorCode.MEMBER_LOGIN_FAIL);
        }

        final String token = tokenProvider.createToken(member.getId(), member.getRole());
        return new MemberLogin.Response(token);
    }

}

package wanted.market.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wanted.market.domain.member.repository.MemberRepository;
import wanted.market.domain.member.repository.entity.Member;
import wanted.market.domain.member.service.dto.request.MemberJoinServiceRequest;
import wanted.market.domain.member.service.dto.request.MemberLoginServiceRequest;
import wanted.market.domain.member.service.dto.response.MemberLoginResponse;
import wanted.market.global.config.jwt.JwtTokenProvider;
import wanted.market.global.exception.CommonErrorCode;
import wanted.market.global.exception.RestApiException;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @Override
    public boolean checkEmail(String email) {
        return memberRepository.findMemberByEmail(email).isEmpty() ? true : false;
    }

    @Override
    public boolean join(MemberJoinServiceRequest request) {
        try {
            memberRepository.save(request.toMember(passwordEncoder));
        } catch (Exception e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }

        return true;
    }

    @Override
    public MemberLoginResponse login(MemberLoginServiceRequest request) {
        Member loginMember = memberRepository.findMemberByEmail(request.getEmail()).orElseThrow(
                () -> new RestApiException(CommonErrorCode.DATA_NOT_FOUND));

        // 유저가 입력한 비밀번호와 DB에 저장된 해시된 비밀번호를 비교
        if (!passwordEncoder.matches(request.getPassword(), loginMember.getPassword())) {
            throw new RestApiException(CommonErrorCode.DATA_NOT_FOUND);
        }

        // 로그인할 때 입력한 이메일과 비밀번호로 인증용 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication();

        // 인증용 토큰으로 실제 존재하는 유저인지 체크
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 생성된 authentication 객체를 사용해 JWT 토큰 생성
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);

        return MemberLoginResponse.builder()
                .accessToken(accessToken)
                .email(loginMember.getEmail())
                .build();
    }
}

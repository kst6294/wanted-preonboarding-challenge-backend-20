package wanted.market.domain.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import wanted.market.domain.member.repository.MemberRepository;
import wanted.market.domain.member.repository.entity.Member;
import wanted.market.domain.member.service.dto.request.MemberJoinServiceRequest;
import wanted.market.domain.member.service.dto.request.MemberLoginServiceRequest;
import wanted.market.domain.member.service.dto.response.MemberLoginResponse;
import wanted.market.global.exception.RestApiException;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static wanted.market.global.dto.Authority.*;


@SpringBootTest
@ActiveProfiles("test")
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입을 시도한 이메일이 중복된 이메일인지 확인한다.")
    void checkEmail() {
        // given
        Member member = createMember("test@test.com");
        memberRepository.save(member);

        String impossibleEmail = member.getEmail();
        String possibleEmail = "test2@test.com";

        // when
        boolean impossibleResult = memberService.checkEmail(impossibleEmail);
        boolean possibleResult = memberService.checkEmail(possibleEmail);

        // then
        assertThat(impossibleResult).isFalse();
        assertThat(possibleResult).isTrue();
    }


    @Test
    @DisplayName("회원가입을 시도한다.")
    void joinMember() {
        // given
        MemberJoinServiceRequest request = MemberJoinServiceRequest.builder()
                .email("test@test.com")
                .password("1234")
                .build();

        // when
        boolean result = memberService.join(request);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("로그인 시나리오")
    @TestFactory
    Collection<DynamicTest> login() {
        // given
        MemberJoinServiceRequest member = createRequestMember("test1@test.com");
        memberService.join(member);

        MemberLoginServiceRequest request1 = createRequestMemberWithEmailAndPassword("test1@test.com", "1234");
        MemberLoginServiceRequest request2 = createRequestMemberWithEmailAndPassword("test2@test.com", "1234");
        MemberLoginServiceRequest request3 = createRequestMemberWithEmailAndPassword("test1@test.com", "4321");

        return List.of(
                DynamicTest.dynamicTest("이메일과 비밀번호가 일치하는 데이터가 있다면 로그인된다.", () -> {
                    // when
                    MemberLoginResponse login = memberService.login(request1);

                    // then
                    assertThat(login.getAccessToken()).isNotEmpty();
                    assertThat(login.getEmail()).isEqualTo(request1.getEmail());
                }),
                DynamicTest.dynamicTest("이메일과 비밀번호중 하나라도 일치하지 않으면 로그인되지 않는다.", () -> {
                    // when & then
                    assertThatThrownBy(() -> memberService.login(request2))
                            .isInstanceOf(RestApiException.class);

                    assertThatThrownBy(() -> memberService.login(request3))
                            .isInstanceOf(RestApiException.class);
                })
        );
    }

    private static Member createMember(String email) {
        return Member.builder()
                .email(email)
                .password("1234")
                .authority(ROLE_USER)
                .build();
    }

    private static MemberJoinServiceRequest createRequestMember(String email) {
        return MemberJoinServiceRequest.builder()
                .email(email)
                .password("1234")
                .build();
    }

    private static MemberLoginServiceRequest createRequestMemberWithEmailAndPassword(String email, String password) {
        return MemberLoginServiceRequest.builder()
                .email(email)
                .password(password)
                .build();
    }
}

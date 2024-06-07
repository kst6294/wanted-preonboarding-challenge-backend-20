package wanted.market.domain.member.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import wanted.market.domain.member.repository.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static wanted.market.global.dto.Authority.ROLE_USER;

@DataJpaTest
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("이메일을 사용한 유저 조회 시나리오")
    @TestFactory
    Collection<DynamicTest> findMemberByEmail() {
        // given
        Member member = createMember();
        memberRepository.save(member);

        return List.of(
                DynamicTest.dynamicTest("유저의 이메일을 토대로 유저 정보를 조회한다.", () -> {
                    // when
                    Optional<Member> memberByEmail = memberRepository.findMemberByEmail(member.getEmail());

                    // then
                    assertThat(memberByEmail).isNotEmpty();
                    assertThat(memberByEmail.get().getEmail()).isEqualTo(member.getEmail());
                }),
                DynamicTest.dynamicTest("존재하지 않는 이메일을 조회하는 경우 조회되지 않는다.", () -> {
                    // when
                    Optional<Member> memberByEmail = memberRepository.findMemberByEmail("noUser@test.com");

                    // then
                    assertThat(memberByEmail).isEmpty();
                })
        );
    }

    @DisplayName("회원가입 시나리오")
    @TestFactory
    Collection<DynamicTest> join() {
        // given
        Member member = createMember();
        memberRepository.save(member);

        Member requestMember1 = createMemberWithSetEmail("test2@test.com");
        Member requestMember2 = createMemberWithSetEmail(member.getEmail());

        return List.of(
                DynamicTest.dynamicTest("존재하지 않는 이메일이므로 해당 이메일로 회원가입이 가능하다.", () -> {
                    // when
                    Optional<Member> memberByEmail = memberRepository.findMemberByEmail(requestMember1.getEmail());

                    // then
                    assertThat(memberByEmail).isEmpty();
                }),
                DynamicTest.dynamicTest("존재하는 이메일이므로 해당 이메일로 회원가입이 불가능하다.", () -> {
                    // when
                    Optional<Member> memberByEmail = memberRepository.findMemberByEmail(requestMember2.getEmail());

                    // then
                    assertThat(memberByEmail).isNotEmpty();
                })
        );
    }

    private static Member createMember() {
        return Member.builder()
                .email("test@test.com")
                .password("1234")
                .authority(ROLE_USER)
                .build();
    }

    private static Member createMemberWithSetEmail(String email) {
        return Member.builder()
                .email(email)
                .password("1234")
                .authority(ROLE_USER)
                .build();
    }

}
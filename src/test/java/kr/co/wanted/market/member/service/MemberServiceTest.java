package kr.co.wanted.market.member.service;

import kr.co.wanted.market.TestUtil;
import kr.co.wanted.market.common.global.utils.TokenProvider;
import kr.co.wanted.market.member.dto.MemberJoin;
import kr.co.wanted.market.member.dto.MemberLogin;
import kr.co.wanted.market.member.entity.Member;
import kr.co.wanted.market.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    TokenProvider tokenProvider;


    @Test
    @DisplayName("로그인 된 유저 찾기_비로그인")
    void find_current_member_test_non_login() {

        // given
        memberRepository.save(
                TestUtil.getMemberArbitraryBuilder()
                        .sample()
        );

        // when then
        assertEquals(Optional.empty(), memberService.findCurrentMember());
    }


    @Test
    @DisplayName("로그인 된 유저 찾기_로그인")
    void find_current_member_test_login() {

        // given
        Member member = memberRepository.save(
                TestUtil.getMemberArbitraryBuilder()
                        .sample()
        );

        // when
        TestUtil.setContextMember(member.getId(), member.getRole());

        // then
        assertEquals(member.getId(), memberService.findCurrentMember().get().getId());
    }


    @Test
    @DisplayName("회원등록")
    void join_member_test() {

        memberService.joinMember(new MemberJoin("test", "test"));

        assertEquals(1, memberRepository.count());
    }


    @Test
    @DisplayName("회원등록 - 로그인ID 중복")
    void id_duplication_join_member_test() {

        // given
        MemberJoin memberJoin = new MemberJoin("test", "test");
        memberService.joinMember(memberJoin);

        // when then
        IntStream.range(0, 3)
                .forEach(i ->
                        assertThrows(
                                DataIntegrityViolationException.class,
                                () -> memberService.joinMember(memberJoin))
                );
    }


    @Test
    @DisplayName("로그인")
    void login_test() {

        // given
        MemberJoin memberJoin = new MemberJoin("test", "test");
        memberService.joinMember(memberJoin);

        // when
        MemberLogin.Response login = memberService.login(new MemberLogin.Request(memberJoin.id(), memberJoin.password()));

        // then
        assertDoesNotThrow(() -> tokenProvider.parse(login.accessToken()));
    }

}
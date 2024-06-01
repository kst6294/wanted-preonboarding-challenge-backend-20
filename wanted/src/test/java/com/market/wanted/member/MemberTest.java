package com.market.wanted.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class MemberTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입")
    @Transactional
    void sign() {
        // given
        Member member = Member.builder()
                .name("name")
                .password("password")
                .username("UserA")
                .build();

        // when
        Member save = memberRepository.save(member);

        // then
        assertEquals(member.getId(), save.getId());
    }

    @Test
    @DisplayName("아이디 중복")
    @Transactional
    void check_id() {
        // given
        MemberSignDto memberSignDto1 = new MemberSignDto();
        memberSignDto1.setName("UserA");
        memberSignDto1.setPassword("password");
        memberSignDto1.setPasswordCheck("password");
        memberSignDto1.setUsername("name");

        MemberSignDto memberSignDto2 = new MemberSignDto();
        memberSignDto2.setName("UserA");
        memberSignDto2.setPassword("password");
        memberSignDto2.setPasswordCheck("password");
        memberSignDto2.setUsername("name");

        memberService.sign(memberSignDto1);

        // when
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> memberService.sign(memberSignDto2));

        // then
        assertEquals(illegalArgumentException.getMessage(), "중복된 아이디입니다.");
    }


    @Test
    @DisplayName("비밀번호 체크")
    @Transactional
    void check_password() {
        // given
        MemberSignDto memberSignDto = new MemberSignDto();
        memberSignDto.setName("UserA");
        memberSignDto.setPassword("password1");
        memberSignDto.setPasswordCheck("password2");
        memberSignDto.setUsername("name");

        // when
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> memberService.sign(memberSignDto));

        // then
        assertEquals(illegalArgumentException.getMessage(), "패스워드를 확인해주세요.");
    }
}
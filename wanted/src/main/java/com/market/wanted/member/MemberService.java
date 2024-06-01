package com.market.wanted.member;

import com.market.wanted.security.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordService passwordService;

    @Transactional
    public Long sign(MemberSignDto memberSignDto){
        validCheck(memberSignDto);
        Member member = Member.builder()
                .username(memberSignDto.getUsername())
                .password(passwordService.encode(memberSignDto.getPassword()))
                .name(memberSignDto.getName())
                .build();
        memberRepository.save(member);
        return member.getId();
    }

    private void validCheck(MemberSignDto memberSignDto){
        if(!memberSignDto.getPassword().equals(memberSignDto.getPasswordCheck())) throw new IllegalArgumentException("패스워드를 확인해주세요.");
        if(memberRepository.existsByUsername(memberSignDto.getUsername())) throw new IllegalArgumentException("중복된 아이디입니다.");
    }
}

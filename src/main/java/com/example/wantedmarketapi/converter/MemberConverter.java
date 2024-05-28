package com.example.wantedmarketapi.converter;

import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.domain.member.Password;
import com.example.wantedmarketapi.dto.request.MemberRequestDto.*;
import com.example.wantedmarketapi.dto.response.MemberResponseDto.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public static Member toMember(SignUpMemberRequest request) {
        return Member.builder()
                .email(request.getEmail())
                .password(Password.encrypt(request.getPassword(), new BCryptPasswordEncoder()))
                .build();
    }

    public static SignUpMemberResponse toSignUpMemberResponse(Member member) {
        return SignUpMemberResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .build();
    }

}

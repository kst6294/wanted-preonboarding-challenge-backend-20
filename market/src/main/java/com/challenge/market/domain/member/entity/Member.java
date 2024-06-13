package com.challenge.market.domain.member.entity;

import com.challenge.market.web.member.dto.SignInRequest;
import com.challenge.market.web.member.dto.SignUpRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue
    private Long Id;
    private String name;
    private String pw;

    public Member(Long id, String name, String pw) {
        Id = id;
        this.name = name;
        this.pw = pw;
    }

    public static Member from(SignUpRequest request){
        return Member.builder().name(request.getName())
                .pw(request.getName())
                .build();

    }

    public static Member from(SignInRequest request) {
        return Member.builder().name(request.getName()).pw(request.getPw()).build();
    }
}

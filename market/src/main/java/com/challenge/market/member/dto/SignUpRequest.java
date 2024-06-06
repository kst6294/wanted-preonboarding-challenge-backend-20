package com.challenge.market.member.dto;

import com.challenge.market.member.domain.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String pw;


    /**
     * Request 객체를 Member 로 변환
     * @return Member.class
     */
    public Member toMember(){
        return Member.builder()
                .name(this.getName())
                .pw(this.getPw())
                .build();
    }


}

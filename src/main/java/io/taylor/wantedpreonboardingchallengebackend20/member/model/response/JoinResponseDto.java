package io.taylor.wantedpreonboardingchallengebackend20.member.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import io.taylor.wantedpreonboardingchallengebackend20.member.entity.Member;

@Data
@NoArgsConstructor
public class JoinResponseDto {
    private String name;
    private String nickName;
    private String email;

    public JoinResponseDto(Member member) {
        this.name = member.getName();
        this.nickName = member.getNickName();
        this.email = member.getEmail();
    }
}

package org.example.wantedpreonboardingchallengebackend20.member.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.wantedpreonboardingchallengebackend20.member.entity.Member;

@Data
@NoArgsConstructor
public class MemberJoinResponse {
    private String name;
    private String nickName;
    private String email;

    public MemberJoinResponse(Member member) {
        this.name = member.getName();
        this.nickName = member.getNickName();
        this.email = member.getEmail();
    }
}

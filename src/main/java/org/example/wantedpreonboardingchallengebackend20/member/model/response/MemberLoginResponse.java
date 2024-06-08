package org.example.wantedpreonboardingchallengebackend20.member.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.wantedpreonboardingchallengebackend20.member.entity.Member;

@Data
@NoArgsConstructor
public class MemberLoginResponse {
    private String name;
    private String nickName;
    private String token;

    public MemberLoginResponse(Member member, String token) {
        this.name = member.getName();
        this.nickName = member.getNickName();
        this.token = token;
    }
}

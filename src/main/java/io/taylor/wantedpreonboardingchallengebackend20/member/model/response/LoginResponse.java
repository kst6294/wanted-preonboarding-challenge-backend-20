package io.taylor.wantedpreonboardingchallengebackend20.member.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import io.taylor.wantedpreonboardingchallengebackend20.member.entity.Member;

@Data
@NoArgsConstructor
public class LoginResponse {
    private String name;
    private String nickName;
    private String token;

    public LoginResponse(Member member, String token) {
        this.name = member.getName();
        this.nickName = member.getNickName();
        this.token = token;
    }
}

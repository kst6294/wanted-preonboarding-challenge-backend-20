package io.taylor.wantedpreonboardingchallengebackend20.member.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JoinReqeust{
    private String name;
    private String nickName;
    private String email;
    private String password;
    private String checkPassword;
}

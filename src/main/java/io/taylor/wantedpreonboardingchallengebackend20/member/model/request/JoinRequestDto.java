package io.taylor.wantedpreonboardingchallengebackend20.member.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequestDto {
    private String name;
    private String nickName;
    private String email;
    private String password;
}

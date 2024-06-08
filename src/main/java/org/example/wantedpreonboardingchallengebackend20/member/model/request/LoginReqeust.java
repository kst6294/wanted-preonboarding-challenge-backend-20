package org.example.wantedpreonboardingchallengebackend20.member.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginReqeust {
    private String email;
    private String password;

    public LoginReqeust(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

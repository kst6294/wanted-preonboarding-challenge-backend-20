package org.example.wantedpreonboardingchallengebackend20.member.model;

import lombok.Data;

@Data
public class Member {
    private String name;
    private String nickName;
    private String email;
    private String password;

    public Member(String name, String nickName, String email, String password) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        encryptPassword(password);
    }

    private void encryptPassword(String password) {
        this.password = password.substring(0, 3) + "****" + password.substring(password.length() - 3);
    }
}

package org.example.wantedpreonboardingchallengebackend20.member.entity;

import org.example.wantedpreonboardingchallengebackend20.member.model.request.MemberReqeust;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="members")
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String nickName;
    @Column(unique = true)
    private String email;
    @Column
    private String password;

    public Member(MemberReqeust memberReqeust) {
        this.name = memberReqeust.name();
        this.nickName = memberReqeust.nickName();
        this.email = memberReqeust.email();
        encryptPassword(memberReqeust.password());
    }

    public Member(String email, String password) {
        this.email = email;
        encryptPassword(password);
    }

    private void encryptPassword(String password) {
        this.password = password.substring(0, 3) + "****" + password.substring(password.length() - 3);
    }
}

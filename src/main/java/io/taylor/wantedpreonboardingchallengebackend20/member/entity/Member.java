package io.taylor.wantedpreonboardingchallengebackend20.member.entity;

import io.taylor.wantedpreonboardingchallengebackend20.member.model.request.JoinReqeust;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String nickName;
    @Column(unique = true)
    private String email;
    @Column
    private String password;
    @LastModifiedDate
    private Timestamp updatedAt;
    @CreatedDate
    private Timestamp createdAt;

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member(JoinReqeust request) {
        this.name = request.getName();
        this.nickName = request.getNickName();
        this.email = request.getEmail();
        this.password = request.getPassword();
    }
}

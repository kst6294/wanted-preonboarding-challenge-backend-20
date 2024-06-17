package io.taylor.wantedpreonboardingchallengebackend20.member.entity;

import io.taylor.wantedpreonboardingchallengebackend20.member.model.request.JoinRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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
    @UpdateTimestamp
    private Timestamp updatedAt;
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member(JoinRequestDto request) {
        this.name = request.getName();
        this.nickName = request.getNickName();
        this.email = request.getEmail();
        this.password = request.getPassword();
    }
}

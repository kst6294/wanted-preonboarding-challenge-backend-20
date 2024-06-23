package kr.co.wanted.market.member.entity;

import jakarta.persistence.*;
import kr.co.wanted.market.common.global.entity.BaseEntity;
import kr.co.wanted.market.common.global.enums.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = {
                @Index(name = "MEMBER_IDX_1", columnList = "loginId", unique = true)
        }
)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;


    public Member(String loginId,
                  String password,
                  Role role) {

        this.loginId = loginId;
        this.password = password;
        this.role = role;
    }

}

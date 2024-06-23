package com.wanted.market.member.domain;

import com.wanted.market.BaseEntity;
import com.wanted.market.member.model.MemberRole;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AttributeOverride(name = "id", column = @Column(name = "member_id"))
public class Member extends BaseEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "member_id")
//    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, name = "member_name")
    private String name;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public Member(Integer id, String email, String name, String password, MemberRole role) {
        super.setId(id);
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }
}

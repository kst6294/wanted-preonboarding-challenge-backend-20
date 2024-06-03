package org.example.preonboarding.member.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.preonboarding.common.entity.BaseEntity;
import org.example.preonboarding.member.model.enums.Role;
import org.example.preonboarding.product.model.entity.Product;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@DynamicUpdate
@Table(name = "users")
@Comment("사용자")
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Comment("사용자 id")
    private String userId;

    @Column(nullable = false)
    @Comment("비밀번호")
    private String password;

    @Column(nullable = false)
    @Comment("사용자 이름")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("권한")
    private Role role;

    @Column
    @Comment("탈퇴일시")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Product> products;

}

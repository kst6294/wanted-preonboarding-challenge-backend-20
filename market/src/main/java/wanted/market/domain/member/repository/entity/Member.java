package wanted.market.domain.member.repository.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.market.global.dto.Authority;
import wanted.market.global.entity.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    private Member(String email, String password, Authority authority) {
        this.email = email;
        this.password = password;
        this.authority = authority;
    }
}

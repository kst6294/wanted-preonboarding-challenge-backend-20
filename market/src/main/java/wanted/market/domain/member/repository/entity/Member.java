package wanted.market.domain.member.repository.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.market.global.dto.Authority;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    private LocalDateTime createdAt;

    @Builder
    private Member(String email, String password, Authority authority, LocalDateTime createdAt) {
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.createdAt = LocalDateTime.now();
    }
}
